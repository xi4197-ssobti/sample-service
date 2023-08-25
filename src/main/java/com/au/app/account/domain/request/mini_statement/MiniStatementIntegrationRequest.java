package com.au.app.account.domain.request.mini_statement;


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
public class MiniStatementIntegrationRequest {

    @JsonProperty("AccountID")
    private String accountID;
    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("ReferenceNumber")
    private String referenceNumber;
    @JsonProperty("Channel")
    private String channel;
    @JsonProperty("TransactionBranch")
    private String transactionBranch;

    @JsonProperty("PageSize")
    private String pageSize;
    @JsonProperty("PageNumber")
    private String pageNumber;
    @JsonProperty("FromDate")
    private String fromDate;
    @JsonProperty("ToDate")
    private String toDate;

    @JsonProperty("NoOftransactions")
    private String noOftransactions;

    @JsonProperty("TransactionDate")
    private String transactionDate;
}
