package com.au.app.account.dto.monthly_account_balance;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BalanceDetailDto {

    private String averageAmountType;
    private String logMonth;
    private String avgBalance;
   }

