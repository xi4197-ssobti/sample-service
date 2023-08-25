package com.au.app.account.domain.request.dedupe_lite_request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonSerialize
@Data
@AllArgsConstructor
@RequiredArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder(builderClassName = "Builder")
public class DedupeLiteRequestIntegration {

    @NotBlank
    @JsonProperty("MobileNumber")
    private String mobileNumber;
    @JsonProperty("AadhaarNumber")
    private String aadhaarNumber;
    @NotBlank
    @JsonProperty("RequestId")
    private String requestId;
    @NotBlank
    @JsonProperty("Channel")
    private String channel;
    @NotBlank
    @JsonProperty("ReferenceNumber")
    private String referenceNumber;
    @JsonProperty("PanNumber")
    private String panNumber;
    @JsonProperty("CustomerId")
    private String customerId;
    @NotBlank
    @JsonProperty("TransactionBranch")
    private String transactionBranch;
}
