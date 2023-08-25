package com.au.app.account.domain.request.monthly_account_balance;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class MonthlyAccountBalanceRequest {

    @NotBlank
    @JsonProperty("AccountId")
    private String accountId;

    @NotBlank
    @JsonProperty("TransactionBranch")
    private String transactionBranch;

}





