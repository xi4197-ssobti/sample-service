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
public class PeriodicStatementRequest {

    @JsonProperty("TransactionBranch")
    private String transactionBranch;
    @JsonProperty("AccountNumber")
    private String accountNumber;
    @JsonProperty("StartDate")
    private String startDate;
    @JsonProperty("EndDate")
    private String endDate;

}
