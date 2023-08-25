package com.au.app.account.domain.request.customer_three_sixty;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Customer360IntegrationRequest {

    @JsonProperty(value = "RequestId")
    private String requestId;

    @JsonProperty(value = "Channel")
    private String channel;

    @NotBlank
    @JsonProperty(value = "CustomerId")
    private String customerId;

    @JsonProperty(value = "ReferenceNumber")
    private String referenceNumber;

    @JsonProperty(value = "TransactionBranch")
    private String transactionBranch;
}
