package com.au.app.account.domain.response.periodic_statement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiniStatementDetails {

    @JsonProperty("TransactionDate")
    private String transactionDate;
    @JsonProperty("ValueDate")
    private String valueDate;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("TransactionType")
    private String transactionType;
    @JsonProperty("TransactionBranch")
    private String transactionBranch;
    @JsonProperty("TransactionAmount")
    private String transactionAmount;
    @JsonProperty("ChequeNumber")
    private String chequeNumber;
    @JsonProperty("RunningTotal")
    private String runningTotal;
    @JsonProperty("TxnId")
    private String txnId;
}
