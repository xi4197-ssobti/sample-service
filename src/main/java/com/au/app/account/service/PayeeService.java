package com.au.app.account.service;

import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.request.payee.DeactivatePayeeRequest;
import com.au.app.account.domain.request.payee.GetPayeeRequest;
import com.au.app.account.domain.request.payee.UpdatePayeeDetailsRequest;
import com.au.app.account.domain.request.payee_account_validation.PayeeAccountValidationRequest;
import com.au.app.account.domain.request.verify_payee_name.VerifyPayeeNameRequest;
import com.au.app.account.domain.response.VerifyResponse;
import com.au.app.account.domain.response.payee.PayeeDetailsResponse;

import java.util.List;


public interface PayeeService {

    String addPayee(AddPayeeRequest addPayeeRequest);

    VerifyResponse verifyPayeeName(VerifyPayeeNameRequest verifyPayeeNameRequest);

    VerifyResponse validatePayeeAccount(PayeeAccountValidationRequest payeeAccountValidationRequest);

    List<PayeeDetailsResponse> getPayee(GetPayeeRequest getPayeeRequest);

    String deactivatePayee(DeactivatePayeeRequest deactivatePayeeRequest);

    String updatePayeeDetails(UpdatePayeeDetailsRequest updatePayeeDetailsRequest);

}
