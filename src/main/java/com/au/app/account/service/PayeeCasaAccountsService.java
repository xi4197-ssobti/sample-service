package com.au.app.account.service;

import com.au.app.account.domain.request.payee_accounts.PayeeAccountsRequest;
import com.au.app.account.domain.response.customer_three_sixty.CustomerAccount;

import java.util.List;

public interface PayeeCasaAccountsService {

    List<CustomerAccount> getCasaAccountsfromMobileNo(PayeeAccountsRequest payeeAccountsRequest);
}
