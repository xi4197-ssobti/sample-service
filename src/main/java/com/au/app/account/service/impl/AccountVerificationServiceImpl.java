package com.au.app.account.service.impl;

import com.au.app.account.client.AccountVerificationClient;
import com.au.app.account.config.ESBHeaderProps;
import com.au.app.account.domain.entity.AccountVerificationStaging;
import com.au.app.account.domain.request.account_verification.AccountVerificationESBRequest;
import com.au.app.account.domain.request.account_verification.AccountVerificationRequest;
import com.au.app.account.domain.response.account_verification.AccountVerificationESBResponse;
import com.au.app.account.repository.AccountVerificationStagingRepository;
import com.au.app.account.service.AccountVerificationService;
import com.au.app.exception.AuBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.au.app.account.constant.AccountServiceConstants.*;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.*;
import static com.au.app.account.util.JsonUtil.getJsonString;

@Service
@RequiredArgsConstructor
@Slf4j
public class AccountVerificationServiceImpl implements AccountVerificationService {
    private final ESBHeaderProps headerProps;
    private final AccountVerificationClient accountVerificationClient;
    private final AccountVerificationStagingRepository accountVerificationStagingRepository;

    @Override
    public AccountVerificationESBResponse getAccountVerificationService(
            AccountVerificationRequest accountVerificationRequest) {

        final String uuid = RandomStringUtils.randomNumeric(20);
        final AccountVerificationESBRequest request = getAccountVerificationESBRequest(accountVerificationRequest,
                uuid);
        log.info("Start Account Verification Request ********** request = {}", getJsonString(request));
        AccountVerificationESBResponse response = null;
        try {
            response = accountVerificationClient.getAccountVerificationESBResponse(headerProps.getHeaderMap(), request);
            log.info("Response for Account Verification {}", getJsonString(request));
        } catch (Exception e) {
            log.error("Error getting the response from ESB for Account Verification {}", e.toString());
            throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
        } finally {
            final AccountVerificationStaging staging = new AccountVerificationStaging();
            staging.setAccountVerificationRequest(getJsonString(request));
            staging.setAccountNumber(request.getBeneficiaryAccountNo());
            staging.setAccountVerificationResponse(getJsonString(response));
            accountVerificationStagingRepository.save(staging);
            log.info("Account Verification response added successfully for Account ID = {}",
                    request.getBeneficiaryAccountNo());
        }
        if (Objects.isNull(response)) {
            log.info("Response from Account Verification ESB api is null");
            throw new AuBusinessException(AS0808.getHttpStatus(), AS0808.getCode(), AS0808.getMessage());

        } else if (response.getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
            log.info("Error getting response for Account Verification for AccountId: {} response: {}",
                    request.getBeneficiaryAccountNo(), response);
            throw new AuBusinessException(AS0809.getHttpStatus(), AS0809.getCode(),
                    response.getTransactionStatus().getExtendedErrorDetails().getMessages().get(0).getMessage());
        } else if (response.getTransactionStatus().getExtendedErrorDetails().getMessages().get(0).getMessage().equals(
                ERROR_MESSAGE)) {
            log.info("Error getting response for Account Verification for AccountId: {} because account "
                     + "not exist response: {}", request.getBeneficiaryAccountNo(), response);
            throw new AuBusinessException(AS0809.getHttpStatus(), AS0809.getCode(),
                    response.getTransactionStatus().getExtendedErrorDetails().getMessages().get(0).getMessage());
        }
        return response;
    }

    private AccountVerificationESBRequest getAccountVerificationESBRequest(
            AccountVerificationRequest accountVerificationRequest, String uuid) {
        return AccountVerificationESBRequest.builder().referenceNumber(uuid).requestId(uuid).originatingChannel(CHANNEL)
                       .transactionBranch(accountVerificationRequest.getTransactionBranch()).beneficiaryAccountNo(
                        accountVerificationRequest.getBeneficiaryAccountNo()).beneficiaryIFSCCode(
                        accountVerificationRequest.getBeneficiaryIFSCCode()).beneficiaryMobileNo(
                        accountVerificationRequest.getBeneficiaryMobileNo()).paymentMethod(PAYMENT_METHOD_TYPE)
                       .remitterAccountNo(accountVerificationRequest.getRemitterAccountNo()).flgIntraBankAllowed(
                        FLG_INTRA_BANK_ALLOWED).retrievalReferenceNumber(RandomStringUtils.randomNumeric(20))
                       .inquiryType(INQUIRY_TYPE).remarks(REMARK).build();
    }
}
