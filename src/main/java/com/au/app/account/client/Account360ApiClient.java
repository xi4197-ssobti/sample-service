package com.au.app.account.client;

import com.au.app.account.client.fallback.Account360ApiClientFallback;
import com.au.app.account.domain.request.account_three_sixty.Account360IntegrationRequest;
import com.au.app.account.domain.response.account_three_sixty.Account360Response;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "Account360", url = "${client.integration.base.url}",
        fallback = Account360ApiClientFallback.class)
public interface Account360ApiClient {
    @PostMapping(value = "/esb-get-account360", consumes = APPLICATION_JSON_VALUE,
                 produces = APPLICATION_JSON_VALUE)
    Account360Response getResponseFromAccount360ESB(@RequestBody Account360IntegrationRequest account360EsbRequest);

}
