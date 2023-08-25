package com.au.app.account.controller;

import com.au.app.account.domain.request.payee_accounts.PayeeAccountsRequest;
import com.au.app.account.domain.response.customer_three_sixty.CustomerAccount;
import com.au.app.account.service.PayeeCasaAccountsService;
import com.au.app.rest.commons.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PayeeCasaAccountsController {

    private final PayeeCasaAccountsService payeeCasaAccountsService;

    @PostMapping("/payee-casa-accounts")
    @Operation(description = "payee accounts", summary = "Call this api to get all casa accounts using mobile number")
    public ApiResponse<List<CustomerAccount>> payeeAccounts(
            @Valid @RequestBody PayeeAccountsRequest payeeAccountsRequest) {
        log.info("Payee Account Request received: {}", payeeAccountsRequest);
        return ApiResponse.success(payeeCasaAccountsService.getCasaAccountsfromMobileNo(payeeAccountsRequest));
    }
}
