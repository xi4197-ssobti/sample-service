package com.au.app.account.domain.request.account_three_sixty;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Account360Request {
    @NotBlank
    @JsonProperty(value = "AccountNumber")
    private String accountNumber;
    @NotBlank
    @JsonProperty(value = "TransactionBranch")
    private String transactionBranch;
    @JsonProperty(value = "CustomerId")
    private String customerId;
    @NotNull
    @JsonProperty(value = "ForAddPayee")
    private Boolean forAddPayee;
    @JsonProperty(value = "PayeeId")
    private String payeeId;

}

