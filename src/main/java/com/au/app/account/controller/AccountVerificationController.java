package com.au.app.account.controller;

import com.au.app.account.domain.request.account_verification.AccountVerificationRequest;
import com.au.app.account.domain.response.account_verification.AccountVerificationESBResponse;
import com.au.app.account.service.AccountVerificationService;
import com.au.app.rest.commons.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class AccountVerificationController {
    private final AccountVerificationService accountVerificationService;

    @PostMapping("/account-verification")
    @Operation(description = "IMPS Beneficiary",
               summary = "Call IMPS Beneficiary Api to verify all the other bank account using account no and IFSC")
    public ApiResponse<AccountVerificationESBResponse> getAccountVerification(
            @Valid @RequestBody AccountVerificationRequest accountVerificationRequest) {
        log.info("Inside AccountVerification : Account Verification Request received: {}", accountVerificationRequest);
        return ApiResponse.success(
                accountVerificationService.getAccountVerificationService(accountVerificationRequest));
    }
}
