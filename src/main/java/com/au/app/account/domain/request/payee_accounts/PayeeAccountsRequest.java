package com.au.app.account.domain.request.payee_accounts;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@JsonSerialize
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayeeAccountsRequest {

    @NotBlank
    @JsonProperty("MobileNumber")
    private String mobileNumber;

    @NotBlank
    @JsonProperty("TransactionBranch")
    private String transactionBranch;
}
