package com.au.app.account.domain.response.account_three_sixty;

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
public class CustomerBasicInquiry {
    @JsonProperty("CustomerId")
    private String customerId;
    @JsonProperty("NationalIdentificationCode")
    private String nationalIdentificationCode;
    @JsonProperty("CustomerName")
    private CustomerName customerName;
    @JsonProperty("CustomerFullName")
    private String customerFullName;
    @JsonProperty("OfficerID")
    private String officerID;
    @JsonProperty("CustomerAddress")
    private CustomerAddress customerAddress;
    @JsonProperty("BirthDateText")
    private String birthDateText;
    @JsonProperty("CategoryType")
    private String categoryType;
    @JsonProperty("Sex")
    private String sex;
    @JsonProperty("IsImageAvailable")
    private String isImageAvailable;
    @JsonProperty("IsSignatureAvailable")
    private String isSignatureAvailable;
    @JsonProperty("CombWithdrawBal")
    private String combWithdrawBal;
    @JsonProperty("AgeOfCustRel")
    private String ageOfCustRel;
    @JsonProperty("AadhaarDetail")
    private AadhaarDetail aadhaarDetail;
    @JsonProperty("HomeBranch")
    private String homeBranch;
    @JsonProperty("MobileNumber")
    private String mobileNumber;
    @JsonProperty("IcType")
    private String icType;
    @JsonProperty("IcTypeDesc")
    private String icTypeDesc;
    @JsonProperty("BankShortName")
    private String bankShortName;
}
