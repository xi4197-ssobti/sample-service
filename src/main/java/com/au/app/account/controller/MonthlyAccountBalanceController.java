package com.au.app.account.controller;

import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceRequest;
import com.au.app.account.dto.monthly_account_balance.MonthlyAccountBalanceDataDto;
import com.au.app.account.service.MonthlyAccountBalanceService;
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
public class MonthlyAccountBalanceController {

    private final MonthlyAccountBalanceService monthlyAccountBalanceService;

    @PostMapping("/monthly-account-balance")
    @Operation(description = "Monthly Account Balance",
               summary = "Monthly Account Balance of CASA Account details " + "using Account ID")
    public ApiResponse<MonthlyAccountBalanceDataDto> monthlyAccountBalance(
            @Valid @RequestBody MonthlyAccountBalanceRequest monthlyAccountBalanceRequest) {
        log.info("Monthly Account Balance Request received: {}", monthlyAccountBalanceRequest);
        return ApiResponse.success(
                monthlyAccountBalanceService.getMonthlyAccountBalanceESB(monthlyAccountBalanceRequest));
    }


}
