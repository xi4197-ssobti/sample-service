package com.au.app.account.client;

import com.au.app.account.client.fallback.Customer360ApiClientFallback;
import com.au.app.account.domain.request.customer_three_sixty.Customer360IntegrationRequest;
import com.au.app.account.domain.response.customer_three_sixty.Customer360Response;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@FeignClient(name = "Customer-360-from-integration", url = "${client.integration.base.url}",
             fallback = Customer360ApiClientFallback.class)
public interface Customer360ApiClient {

    @PostMapping(value = "/esb-get-customer360", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    Customer360Response getCasaAccounts(@RequestBody
    Customer360IntegrationRequest customer360IntegrationRequest);

}
