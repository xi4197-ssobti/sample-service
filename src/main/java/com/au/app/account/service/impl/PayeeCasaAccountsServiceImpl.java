package com.au.app.account.service.impl;

import com.au.app.account.client.Customer360ApiClient;
import com.au.app.account.client.DedupeLiteApiClient;
import com.au.app.account.config.CurrentStatusProps;
import com.au.app.account.config.ModuleCodeProps;
import com.au.app.account.domain.request.customer_three_sixty.Customer360IntegrationRequest;
import com.au.app.account.domain.request.dedupe_lite_request.DedupeLiteRequestIntegration;
import com.au.app.account.domain.request.payee_accounts.PayeeAccountsRequest;
import com.au.app.account.domain.response.customer_three_sixty.Customer360Response;
import com.au.app.account.domain.response.customer_three_sixty.CustomerAccount;
import com.au.app.account.domain.response.dedupe_lite.Customer;
import com.au.app.account.domain.response.dedupe_lite.DedupeLiteResponseIntegration;
import com.au.app.account.service.PayeeCasaAccountsService;
import com.au.app.exception.AuBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.au.app.account.constant.AccountServiceConstants.ACTIVE_CIF_STATUS;
import static com.au.app.account.constant.AccountServiceConstants.CHANNEL;
import static com.au.app.account.constant.AccountServiceConstants.ERROR_CODE;
import static com.au.app.account.constant.AccountServiceConstants.NOT_REQUIRED;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0500;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0808;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0809;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayeeCasaAccountsServiceImpl implements PayeeCasaAccountsService {

    private final DedupeLiteApiClient dedupeLiteApiClient;
    private final Customer360ApiClient customer360ApiClient;
    private final CurrentStatusProps currentStatusProps;
    private final ModuleCodeProps moduleCodeProps;
    private final ExecutorService executorThreads1 = Executors.newFixedThreadPool(10);

    @Override
    public List<CustomerAccount> getCasaAccountsfromMobileNo(PayeeAccountsRequest payeeAccountsRequest) {

        final String uuid = UUID.randomUUID().toString();
        final DedupeLiteRequestIntegration dedupeRequest = getDedupeRequest(payeeAccountsRequest, uuid);
        log.info("Start getCasaAccountsfromMobileNo() ********** request = {}", dedupeRequest);
        final DedupeLiteResponseIntegration dedupeResponse;

        try {
            dedupeResponse = dedupeLiteApiClient.getCasaAccounts(dedupeRequest);
            log.info("Response for dedupe lite from integration = {}", dedupeResponse);
        } catch (Exception e) {
            log.error("Error getting the response from dedupe-lite-integration = {}", e.toString());
            throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
        }

        if (Objects.isNull(dedupeResponse)) {
            log.info("Response from DedupeLiteIntegration api is null");
            throw new AuBusinessException(AS0808.getHttpStatus(), AS0808.getCode(), AS0808.getMessage());
        } else if (dedupeResponse.getData().getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
            log.info("DedupeResponse error getting response for DedupeLiteIntegration for mobileNumber = {} "
                     + "response: {}", dedupeRequest.getMobileNumber(), dedupeResponse);
            throw new AuBusinessException(AS0809.getHttpStatus(), AS0809.getCode(),
                    dedupeResponse.getData().getTransactionStatus().getExtendedErrorDetails().getMessages()
                            .getMessage());
        }

        List<Customer> activeCifs = dedupeResponse.getData().getMatchFound().getCustomer().stream().filter(
                e -> e.getBlockStatus().equalsIgnoreCase(ACTIVE_CIF_STATUS)).toList();

        List<CustomerAccount> customerAccounts = new ArrayList<>();
        final String transactionBranch = payeeAccountsRequest.getTransactionBranch();

        for (Customer customer : activeCifs) {

            final String custUuid = UUID.randomUUID().toString();
            final Customer360IntegrationRequest customer360Request = createCustomer360Req(customer.getCustomerID(),
                    transactionBranch, custUuid);
            List<CustomerAccount> activeAccounts;

            CompletableFuture<Customer360Response> customer360ResponseFuture = CompletableFuture.supplyAsync(() -> {
                Customer360Response customer360Response;

                customer360Response = customer360ApiClient.getCasaAccounts(customer360Request);
                log.info("Response for Customer360 from integration = {}", customer360Response);

                return customer360Response;

            }, executorThreads1).thenApply(customer360Response -> {
                if (customer360Response.getData().getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
                    log.info("Customer360Response error getting response for Customer360-integration for customerId "
                             + "************************* " + "{} response: {}", customer.getCustomerID(),
                            customer360Response);
                }
                return customer360Response;
            }).exceptionally(ex -> {
                log.error("Error getting the response from Customer360-integration = {}", ex.toString());
                throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
            });

            try {
                activeAccounts =
                        customer360ResponseFuture.join().getData().getAccountDetails().getCustomerAccount().stream()
                                .filter(e -> (currentStatusProps.getDepositSummary().contains(e.getCurrentStatus())
                                              && moduleCodeProps.getModuleCodeFilter().contains(e.getModuleCode())))
                                .toList();
                customerAccounts.addAll(activeAccounts);
            } catch (Exception e) {
                log.error("Error while joining threads = {}", e.toString());
                throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
            }
        }
        return customerAccounts;
    }

    private Customer360IntegrationRequest createCustomer360Req(String customerId, String transactionBranch,
            String uuid) {
        return Customer360IntegrationRequest.builder().requestId(uuid).customerId(customerId).channel(CHANNEL)
                       .referenceNumber(uuid).transactionBranch(transactionBranch).build();
    }

    private DedupeLiteRequestIntegration getDedupeRequest(PayeeAccountsRequest payeeAccountsRequest, String uuid) {
        return DedupeLiteRequestIntegration.builder().requestId(uuid).referenceNumber(uuid).mobileNumber(
                        payeeAccountsRequest.getMobileNumber()).channel(CHANNEL).transactionBranch(
                        payeeAccountsRequest.getTransactionBranch()).customerId(NOT_REQUIRED).panNumber(NOT_REQUIRED)
                       .aadhaarNumber(NOT_REQUIRED).build();
    }
}
