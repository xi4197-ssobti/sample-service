package com.au.app.account.client;

import com.au.app.account.client.fallback.DebitCardDetailsClientFallback;
import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsIntegrationRequest;
import com.au.app.account.domain.response.debit_card_details.DebitCardDetailsResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@FeignClient(name = "DebitCardDetails", url = "${client.integration.base.url}",
             fallback = DebitCardDetailsClientFallback.class)
public interface DebitCardDetailsApiClient {

    @PostMapping(value = "/debit-card-details", consumes = APPLICATION_JSON_VALUE,
                 produces = APPLICATION_JSON_VALUE)
    DebitCardDetailsResponse getDebitCardDetailsResponse(
            @RequestBody DebitCardDetailsIntegrationRequest debitCardDetailsIntegrationRequest);

}
