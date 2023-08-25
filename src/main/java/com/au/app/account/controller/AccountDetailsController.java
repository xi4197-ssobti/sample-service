package com.au.app.account.controller;

import com.au.app.account.domain.request.account_three_sixty.Account360Request;
import com.au.app.account.domain.response.account_three_sixty.Account360DetailResponse;
import com.au.app.account.service.Account360Service;
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
public class AccountDetailsController {
    private final Account360Service account360Service;

    @PostMapping("/internal-account-verification")
    @Operation(description = "Account 360",
            summary = "Call this Api to verify any AU account number.")
    public ApiResponse<Account360DetailResponse> getAccount360Data(@Valid @RequestBody Account360Request request) {
        log.info("Inside getAccount360Data() Controller - Request received: {}", request);
        return ApiResponse.success(account360Service.account360IntegrationDetails(request));
    }
}
