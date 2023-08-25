package com.au.app.account.client;

import com.au.app.account.client.fallback.MonthlyAccountBalanceApiClientFallback;
import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceIntegrationRequest;
import com.au.app.account.domain.response.monthly_account_balance.MonthlyAccountBalanceResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@FeignClient(name = "Monthly-Account-Balance", url = "${client.integration.base.url}",
             fallback = MonthlyAccountBalanceApiClientFallback.class)
public interface MonthlyAccountBalanceApiClient {

    @PostMapping(value = "/monthly-average-balance", consumes = APPLICATION_JSON_VALUE,
                 produces = APPLICATION_JSON_VALUE) MonthlyAccountBalanceResponse monthlyAccountBalance(
            @RequestBody MonthlyAccountBalanceIntegrationRequest monthlyAccountBalanceIntegrationRequest);

}
