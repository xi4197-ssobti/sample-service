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
public class AccountVerificationESBRequest {

    @NotBlank
    @JsonProperty(value = "ReferenceNumber")
    private String referenceNumber;

    @NotBlank
    @JsonProperty(value = "TransactionBranch")
    private String transactionBranch;

    @NotBlank
    @JsonProperty(value = "RequestId")
    private String requestId;

    @NotBlank
    @JsonProperty(value = "OriginatingChannel")
    private String originatingChannel;




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
    @JsonProperty(value = "PaymentMethod")
    private String paymentMethod;

    @NotBlank
    @JsonProperty(value = "RemitterAccountNo")
    private String remitterAccountNo;



    @NotBlank
    @JsonProperty(value = "FlgIntraBankAllowed")
    private String flgIntraBankAllowed;

    @NotBlank
    @JsonProperty(value = "InquiryType")
    private String inquiryType;

    @NotBlank
    @JsonProperty(value = "Remarks")
    private String remarks;

    @NotBlank
    @JsonProperty(value = "RetrievalReferenceNumber")
    private String retrievalReferenceNumber;



}
