package com.au.app.account.domain.response.monthly_account_balance;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class BalanceDetail {

    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("AverageAmountType")
    private String averageAmountType;
    @JsonProperty("LogDate")
    private Date logDate;
    @JsonProperty("AvgBalance")
    private String avgBalance;
}
