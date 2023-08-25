package com.au.app.account.client;

import com.au.app.account.client.fallback.DedupeLiteApiClientFallback;
import com.au.app.account.domain.request.dedupe_lite_request.DedupeLiteRequestIntegration;
import com.au.app.account.domain.response.dedupe_lite.DedupeLiteResponseIntegration;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "Dedupe-lite-from-integration", url = "${client.integration.base.url}",
             fallback = DedupeLiteApiClientFallback.class)
public interface DedupeLiteApiClient {

    @PostMapping(value = "/dedupe-lite", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    DedupeLiteResponseIntegration getCasaAccounts(
            @RequestBody DedupeLiteRequestIntegration dedupeLiteRequestIntegration);
}
