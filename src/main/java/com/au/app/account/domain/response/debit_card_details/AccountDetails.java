package com.au.app.account.domain.response.debit_card_details;

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
public class AccountDetails {

    @JsonProperty("AccountType")
    private String accountType;
    @JsonProperty("AccountNumber")
    private String accountNumber;
    @JsonProperty("AccountDescription")
    private String accountDescription;
    @JsonProperty("BranchCode")
    private String branchCode;
    @JsonProperty("CurrencyCode")
    private String currencyCode;
    @JsonProperty("AccountStatus")
    private String accountStatus;
    @JsonProperty("PrimaryFlag")
    private String primaryFlag;
}
