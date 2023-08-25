package com.au.app.account.domain.request.account_verification;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.NotBlank;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AccountVerificationRequest {

    @NotBlank
    @JsonProperty(value = "TransactionBranch")
    private String transactionBranch;

    @NotBlank
    @JsonProperty(value = "BeneficiaryAccountNo")
    private String beneficiaryAccountNo;

    @NotBlank
    @JsonProperty(value = "BeneficiaryIFSCCode")
    private String beneficiaryIFSCCode;


    @NotBlank
    @JsonProperty(value = "BeneficiaryMobileNo")
    private String beneficiaryMobileNo;


    @NotBlank
    @JsonProperty(value = "RemitterAccountNo")
    private String remitterAccountNo;
}
