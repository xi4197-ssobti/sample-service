package com.au.app.account.domain.response.customer_three_sixty;

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
public class Customer360DetailResponse {

    @JsonProperty("TransactionStatus")
    private TransactionStatus transactionStatus;

    @JsonProperty("AccountDetails")
    private AccountDetails accountDetails;

}
