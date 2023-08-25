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
public class MiniStatementRequest {

    @JsonProperty("AccountID")
    private String accountID;
    @JsonProperty("NoOftransactions")
    private String noOftransactions;
    @JsonProperty("TransactionBranch")
    private String transactionBranch;
}
