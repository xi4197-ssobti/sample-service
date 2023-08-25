package com.au.app.account.domain.request.account_three_sixty;

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
public class Account360IntegrationRequest {
    @NotBlank
    @JsonProperty(value = "RequestId")
    private String requestId;
    @NotBlank
    @JsonProperty(value = "Channel")
    private String channel;
    @NotBlank
    @JsonProperty(value = "AccountNumber")
    private String accountNumber;
    @NotBlank
    @JsonProperty(value = "TransactionBranch")
    private String transactionBranch;
    @NotBlank
    @JsonProperty(value = "ReferenceNumber")
    private String referenceNumber;

}

