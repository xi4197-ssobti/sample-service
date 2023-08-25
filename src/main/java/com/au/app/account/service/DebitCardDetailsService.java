package com.au.app.account.service;


import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsRequest;
import com.au.app.account.domain.response.debit_card_details.DebitCardResponseData;

public interface DebitCardDetailsService {
    DebitCardResponseData getDebitCardDetails(DebitCardDetailsRequest debitCardDetailsRequest);
}
