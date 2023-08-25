
package com.au.app.account.domain.response.debit_card_details;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CardDetail {

    @JsonProperty("CardNumber")
    private String cardNumber;
    @JsonProperty("ClientID")
    private String clientID;
    @JsonProperty("MemberNumber")
    private String memberNumber;
    @JsonProperty("StatusAction")
    private Object statusAction;
    @JsonProperty("ActiveInactive")
    private String activeInactive;
    @JsonProperty("ClientDirectoryFirstName")
    private String clientDirectoryFirstName;
    @JsonProperty("ClientDirectoryLastName")
    private String clientDirectoryLastName;
    @JsonProperty("ClientDirectorySearchName")
    private String clientDirectorySearchName;
    @JsonProperty("EmbossName1")
    private String embossName1;
    @JsonProperty("EmbossName2")
    private String embossName2;
    @JsonProperty("EncodeFirstName")
    private String encodeFirstName;
    @JsonProperty("EncodeLastName")
    private String encodeLastName;
    @JsonProperty("PrimaryAcctNumber")
    private String primaryAcctNumber;
    @JsonProperty("PrimaryAcctCurrency")
    private String primaryAcctCurrency;
    @JsonProperty("CardIssueDate")
    private String cardIssueDate;
    @JsonProperty("ExpiryDate")
    private Date expiryDate;
    @JsonProperty("CardType")
    private String cardType;
    @JsonProperty("CardActivationChannel")
    private String cardActivationChannel;
    @JsonProperty("OverseasTransactionActivationStatus")
    private String overseasTransactionActivationStatus;
    @JsonProperty("ReissueCount")
    private String reissueCount;
    @JsonProperty("AccountDetails")
    private AccountDetails accountDetails;
    @JsonProperty("ReplaceCardActivation")
    private String replaceCardActivation;

}
