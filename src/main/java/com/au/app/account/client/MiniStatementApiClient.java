package com.au.app.account.client;

import com.au.app.account.client.fallback.MiniStatementClientFallback;
import com.au.app.account.domain.request.mini_statement.MiniStatementIntegrationRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementIntegrationRequest;
import com.au.app.account.domain.response.mini_statement.MiniStatementResponse;
import com.au.app.account.domain.response.periodic_statement.PeriodicStatementResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@FeignClient(name = "Mini-Statement", url = "${client.integration.base.url}",
             fallback = MiniStatementClientFallback.class)
public interface MiniStatementApiClient {

    @PostMapping(value = "/account-mini-statement", consumes = APPLICATION_JSON_VALUE,
                 produces = APPLICATION_JSON_VALUE)
    MiniStatementResponse getResponseFromMiniStatementESB(
            @RequestBody MiniStatementIntegrationRequest miniStatementIntegrationRequest);

    @PostMapping(value = "/account-statement", consumes = APPLICATION_JSON_VALUE,
                 produces = APPLICATION_JSON_VALUE)
    PeriodicStatementResponse getResponseFromPeriodicStatementESB(
            @RequestBody PeriodicStatementIntegrationRequest periodicStatementIntegrationRequest);

}
