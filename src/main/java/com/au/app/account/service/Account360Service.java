package com.au.app.account.service;

import com.au.app.account.domain.request.account_three_sixty.Account360Request;
import com.au.app.account.domain.response.account_three_sixty.Account360DetailResponse;

public interface Account360Service {
    Account360DetailResponse account360IntegrationDetails(Account360Request account360Request);
}
