package com.au.app.account.controller;

import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsRequest;
import com.au.app.account.domain.response.debit_card_details.DebitCardResponseData;
import com.au.app.account.service.DebitCardDetailsService;
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
public class DebitCardController {

    private final DebitCardDetailsService debitCardDetailsService;

    @PostMapping("/debit-card-details")
    @Operation(description = "Debit Card Details",
               summary = "Call this api for All Debit Card Details using customer ID")
    public ApiResponse<DebitCardResponseData> fetchDebitCardDetails(
            @Valid @RequestBody DebitCardDetailsRequest debitCardDetailsESBRequest) {
        log.info("Inside DebitCardController - Request received: {}", debitCardDetailsESBRequest);
        return ApiResponse.success(debitCardDetailsService.getDebitCardDetails(debitCardDetailsESBRequest));
    }

}
