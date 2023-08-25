package com.au.app.account.client;

import com.au.app.account.client.fallback.AccountVerificationClientFallback;
import com.au.app.account.domain.request.account_verification.AccountVerificationESBRequest;
import com.au.app.account.domain.response.account_verification.AccountVerificationESBResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "AccountVerification", url = "${client.aubank.service.url}",
             fallback = AccountVerificationClientFallback.class)
public interface AccountVerificationClient {

    @PostMapping(value = "/CBSIMPSBeneNameInqService", consumes = APPLICATION_JSON_VALUE,
                 produces = APPLICATION_JSON_VALUE)
    AccountVerificationESBResponse getAccountVerificationESBResponse(
            @RequestHeader Map<String, String> headerMap,
            @RequestBody AccountVerificationESBRequest accountVerificationESBRequest);

}
