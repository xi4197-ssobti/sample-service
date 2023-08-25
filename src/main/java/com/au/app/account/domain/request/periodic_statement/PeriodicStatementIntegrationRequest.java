package com.au.app.account.domain.request.periodic_statement;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PeriodicStatementIntegrationRequest {

    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("OriginatingChannel")
    private String originatingChannel;
    @JsonProperty("ReferenceNumber")
    private String referenceNumber;
    @JsonProperty("TransactionBranch")
    private String transactionBranch;
    @JsonProperty("EndDate")
    private String endDate;
    @JsonProperty("AccountNumber")
    private String accountNumber;
}
