package com.au.app.account.domain.request.payee;

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
public class BankDetailsData {
    @JsonProperty(value = "AccountHolderName")
    private String accountHolderName;
    @JsonProperty(value = "BankDisplayName")
    private String bankDisplayName;
    @JsonProperty(value = "BankName")
    private String bankName;
    @JsonProperty(value = "AccountNumber")
    private String accountNumber;
    @JsonProperty(value = "IfscCode")
    private String ifscCode;
    @JsonProperty(value = "VerificationFlag")
    private Boolean verificationFlag;
    @JsonProperty(value = "IsAdded")
    private Boolean isAdded;
    @JsonProperty(value = "IsRtgsEnabled")
    private Boolean isRtgsEnabled;
    @JsonProperty(value = "IsNeftEnabled")
    private Boolean isNeftEnabled;


}
