package com.au.app.account.domain.response.account_verification;

import com.au.app.account.domain.response.transaction_status.TransactionStatus;
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
public class AccountVerificationESBResponse {

    @JsonProperty("TransactionStatus")
    private TransactionStatus transactionStatus;

    @JsonProperty("BeneficiaryName")
    private String beneficiaryName;

    @JsonProperty("RetrievalReferenceNumber")
    private String retrievalReferenceNumber;
}
