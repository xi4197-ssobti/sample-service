package com.au.app.account.service;

import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceRequest;
import com.au.app.account.dto.monthly_account_balance.MonthlyAccountBalanceDataDto;


public interface MonthlyAccountBalanceService {
    MonthlyAccountBalanceDataDto getMonthlyAccountBalanceESB(
            MonthlyAccountBalanceRequest monthlyAccountBalanceRequest);
}
