package com.au.app.account.service;

import com.au.app.account.domain.request.account_verification.AccountVerificationRequest;
import com.au.app.account.domain.response.account_verification.AccountVerificationESBResponse;

public interface AccountVerificationService {
    AccountVerificationESBResponse getAccountVerificationService(AccountVerificationRequest accountVerificationRequest);
}
