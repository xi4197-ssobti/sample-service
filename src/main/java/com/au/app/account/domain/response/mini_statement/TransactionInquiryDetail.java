package com.au.app.account.domain.response.mini_statement;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionInquiryDetail {

    @JsonProperty("TxnDate")
    private String txnDate;
    @JsonProperty("ValueDate")
    private String valueDate;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("ChequeNo")
    private String chequeNo;
    @JsonProperty("FlgDrCr")
    private String flgDrCr;
    @JsonProperty("OriginatingBranch")
    private String originatingBranch;
    @JsonProperty("Amount")
    private String amount;
    @JsonProperty("RunningTotal")
    private String runningTotal;
    @JsonProperty("DepositNumber")
    private String depositNumber;
}
