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
public class AccountDetail {
    @JsonProperty("ModuleCode")
    private String moduleCode;
    @JsonProperty("ProductName")
    private String productName;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("CurrencyCode")
    private String currencyCode;
    @JsonProperty("CurrencyShortName")
    private String currencyShortName;
    @JsonProperty("CustomerRelationship")
    private String customerRelationship;
    @JsonProperty("BalanceBook")
    private String balanceBook;
    @JsonProperty("UnclearFunds")
    private String unclearFunds;
    @JsonProperty("Classification")
    private String classification;
    @JsonProperty("Reason")
    private String reason;
    @JsonProperty("BillAmount")
    private String billAmount;
    @JsonProperty("OriginalBalance")
    private String originalBalance;
    @JsonProperty("CurrentStatus")
    private String currentStatus;
    @JsonProperty("CurrentStatusDescription")
    private String currentStatusDescription;
    @JsonProperty("AcyAmount")
    private String acyAmount;
    @JsonProperty("AvailableBalance")
    private String availableBalance;
    @JsonProperty("LcyAmount")
    private String lcyAmount;
    @JsonProperty("TotalAcyAmount")
    private String totalAcyAmount;
    @JsonProperty("TotalLcyAmount")
    private String totalLcyAmount;
    @JsonProperty("BranchName")
    private String branchName;
    @JsonProperty("ExternalAccountId")
    private String externalAccountId;
    @JsonProperty("FutureDatedAmount")
    private String futureDatedAmount;
    @JsonProperty("SafeDepositBoxId")
    private String safeDepositBoxId;
    @JsonProperty("DateAccountOpen")
    private String dateAccountOpen;
    @JsonProperty("DateAccountActive")
    private String dateAccountActive;
    @JsonProperty("DateRelation")
    private String dateRelation;
    @JsonProperty("MonthsSinceActive")
    private String monthsSinceActive;
    @JsonProperty("BalUncollectedPrinc")
    private String balUncollectedPrinc;
    @JsonProperty("BalUncollectedInt")
    private String balUncollectedInt;
    @JsonProperty("TotalBalUncollecPrinc")
    private String totalBalUncollecPrinc;
    @JsonProperty("TotalBalUncollecInt")
    private String totalBalUncollecInt;
    @JsonProperty("TotalBalBook")
    private String totalBalBook;
    @JsonProperty("DateValue")
    private String dateValue;
    @JsonProperty("BalPrincipal")
    private String balPrincipal;
    @JsonProperty("IntRate")
    private String intRate;
    @JsonProperty("DepositNo")
    private String depositNo;
    @JsonProperty("LienAmount")
    private String lienAmount;
    @JsonProperty("InstallmentAmount")
    private String installmentAmount;
    @JsonProperty("OtherArrear")
    private String otherArrear;
    @JsonProperty("BalCombinedAcy")
    private String balCombinedAcy;
    @JsonProperty("BalCombinedLcy")
    private String balCombinedLcy;
    @JsonProperty("BranchCode")
    private String branchCode;
    @JsonProperty("IsTDLinkage")
    private String isTDLinkage;
    @JsonProperty("MaturityAmount")
    private String maturityAmount;
}
