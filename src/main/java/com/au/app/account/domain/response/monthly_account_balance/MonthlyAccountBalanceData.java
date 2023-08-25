
package com.au.app.account.domain.response.monthly_account_balance;

import com.au.app.account.domain.response.transaction_status.TransactionStatus;
import com.au.app.account.dto.monthly_account_balance.BalanceDetailDto;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class MonthlyAccountBalanceData {

    @JsonProperty("TransactionStatus")
    private TransactionStatus transactionStatus;
    @JsonProperty("BalanceDetails")
    private List<BalanceDetail> balanceDetails;
    @JsonProperty("UpdatedBalanceDetails")
    private List<BalanceDetailDto> updatedBalanceDetails;

}
