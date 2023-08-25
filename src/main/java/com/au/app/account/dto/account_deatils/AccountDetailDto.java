package com.au.app.account.dto.account_deatils;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AccountDetailDto {

    private String accountName;
    private String logMonth;
    private String avgBalance;
   }

