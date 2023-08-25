package com.au.app.account.domain.response.payee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankDetailsResponse {
    @JsonProperty("AccountHolderName")
    private String accountHolderName;
    @JsonProperty("IfscCode")
    private String ifscCode;
    @JsonProperty("AccountNumber")
    private String accountNumber;
    @JsonProperty("BankName")
    private String bankName;
    @JsonProperty("BankDisplayName")
    private String bankDisplayName;
    @JsonProperty("VerificationFlag")
    private Boolean verificationFlag;
    @JsonProperty("IsAdded")
    private Boolean isAdded;
    @JsonProperty(value = "IsRtgsEnabled")
    private Boolean isRtgsEnabled;
    @JsonProperty(value = "IsNeftEnabled")
    private Boolean isNeftEnabled;
    @JsonProperty("UpdatedAt")
    private OffsetDateTime updatedAt;


}
