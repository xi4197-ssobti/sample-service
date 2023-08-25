package com.au.app.account.service.impl;

import com.au.app.account.client.Account360ApiClient;
import com.au.app.account.domain.entity.Account360Staging;
import com.au.app.account.domain.entity.PayeeDetails;
import com.au.app.account.domain.request.account_three_sixty.Account360IntegrationRequest;
import com.au.app.account.domain.request.account_three_sixty.Account360Request;
import com.au.app.account.domain.response.account_three_sixty.Account360DetailResponse;
import com.au.app.account.domain.response.account_three_sixty.Account360Response;
import com.au.app.account.repository.Account360StagingRepository;
import com.au.app.account.repository.PayeeRepository;
import com.au.app.account.service.Account360Service;
import com.au.app.exception.AuBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static com.au.app.account.constant.AccountServiceConstants.CHANNEL;
import static com.au.app.account.constant.AccountServiceConstants.ERROR_CODE;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0200;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0500;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0809;
import static com.au.app.account.util.JsonUtil.getJsonString;

@Service
@RequiredArgsConstructor
@Slf4j
public class Account360ServiceImpl implements Account360Service {
    private final PayeeRepository payeeRepository;
    private final Account360ApiClient account360ApiClient;
    private final Account360StagingRepository account360StagingRepository;

    @Override
    public Account360DetailResponse account360IntegrationDetails(Account360Request account360Request) {

        if (!Objects.isNull(account360Request.getForAddPayee()) && account360Request.getForAddPayee()) {
            validateAccount(account360Request);
        }

        final String uuid = UUID.randomUUID().toString();
        final Account360IntegrationRequest request = getAccount360IntegrationRequest(account360Request, uuid);

        log.info("Start getAccount360IntegrationDetails() ********** request = {}", request);

        Account360Response response = new Account360Response();

        try {
            response = account360ApiClient.getResponseFromAccount360ESB(request);
            log.info("Response for Account360 {}", response);
        } catch (Exception e) {
            log.error("Error getting the response from ESB for Account360 {}", e.toString());
            throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
        } finally {
            final Account360Staging staging = new Account360Staging();
            staging.setAccountEsbRequest(getJsonString(request));
            staging.setAccountNo(request.getAccountNumber());
            staging.setAccountEsbResponse(getJsonString(response));
            account360StagingRepository.save(staging);
            log.info("Account 360 response added successfully for account-number = {}", request.getAccountNumber());
        }

        if (response.getData().getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
            log.error("Account360 error response- {}", response);
            throw new AuBusinessException(AS0809.getHttpStatus(), AS0809.getCode(),
                    response.getData().getTransactionStatus().getExtendedErrorDetails().getMessages().get(0)
                            .getMessage());
        } else {
            return response.getData();
        }

    }

    private void validateAccount(Account360Request account360Request) {
        List<PayeeDetails> payeeDetailsList = payeeRepository.findByCustomerId(account360Request.getCustomerId());
        if (!payeeDetailsList.isEmpty())
            payeeDetailsList.forEach(payeeDetails -> {
                        if (Objects.isNull(account360Request.getPayeeId())
                                || !(payeeDetails.getId().equals(account360Request.getPayeeId()))) {
                            payeeDetails.getBankDetails().forEach(bankDetails -> {
                                if (bankDetails.getActivationFlag() && bankDetails.getAccountNumber()
                                        .equals(account360Request.getAccountNumber()))
                                    throw new AuBusinessException(AS0200.getHttpStatus(), AS0200.getCode(),
                                            AS0200.getMessage() + payeeDetails.getPayeeName());

                            });
                        }
                    }
            );
    }

    protected Account360IntegrationRequest getAccount360IntegrationRequest(Account360Request account360Request,
                                                                           String uniqueId) {
        return Account360IntegrationRequest.builder().requestId(uniqueId).channel(CHANNEL).accountNumber(
                account360Request.getAccountNumber()).transactionBranch(account360Request
                .getTransactionBranch()).referenceNumber(uniqueId).build();
    }
}