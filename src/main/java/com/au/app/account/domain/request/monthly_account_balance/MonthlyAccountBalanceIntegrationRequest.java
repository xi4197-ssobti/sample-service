package com.au.app.account.domain.request.monthly_account_balance;

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
public class MonthlyAccountBalanceIntegrationRequest {

    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("RequestId")
    private String requestId;
    @JsonProperty("OriginatingChannel")
    private String originatingChannel;
    @JsonProperty("ReferenceNumber")
    private String referenceNumber;
    @JsonProperty("TransactionBranch")
    private String transactionBranch;

}
