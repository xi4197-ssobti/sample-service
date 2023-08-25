
package com.au.app.account.domain.response.mini_statement;

import java.util.List;

import com.au.app.account.domain.response.transaction_status.TransactionStatus;
import com.fasterxml.jackson.annotation.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MiniStatementResponseData {

    @JsonProperty("TransactionStatus")
    private TransactionStatus transactionStatus;
    @JsonProperty("TransactionInquiryDetails")
    private List<TransactionInquiryDetail> transactionInquiryDetails;
    @JsonProperty("ClosingBalance")
    private String closingBalance;
    @JsonProperty("OpeningBalance")
    private String openingBalance;


}
