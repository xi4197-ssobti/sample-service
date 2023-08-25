package com.au.app.account.service;

import com.au.app.account.client.Account360ApiClient;
import com.au.app.account.domain.entity.BankDetails;
import com.au.app.account.domain.entity.PayeeDetails;
import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.request.payee.BankDetailsData;
import com.au.app.account.domain.request.payee.DeactivatePayeeRequest;
import com.au.app.account.domain.request.payee_account_validation.PayeeAccountValidationRequest;
import com.au.app.account.domain.response.account_three_sixty.Account360Response;
import com.au.app.account.repository.Account360StagingRepository;
import com.au.app.account.repository.PayeeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mockito.Mock;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AbstractServiceTest {
    public static final String DUMMY_REQ_ID = UUID.randomUUID().toString();
    public static final String DUMMY_CHANNEL = "AUMB";
    public static final String DUMMY_ACCOUNT_NUMBER = "190998";
    public static final String DUMMY_CUSTOMER_ID = "190998";

    public static final String DUMMY_BRANCH = "2011";
    public static final String DUMMY_PAYEE_NAME = "RAVI";
    public static final String DUMMY_REFERENCE_NUMBER = UUID.randomUUID().toString();

    public static ObjectMapper objectMapper;
    public Account360Service account360Service;
    public PayeeService payeeService;
    @Mock
    public PayeeRepository payeeRepository;
    @Mock
    public Account360ApiClient account360ApiClient;
    @Mock
    public Account360StagingRepository account360StagingRepository;


    public Account360Response mockAccount360Response() {
        Account360Response response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/account_360_response.json")) {
            response = objectMapper.readValue(in, Account360Response.class);


        } catch (Exception e) {

        }
        return response;
    }

    public AddPayeeRequest mockAddPayeeRequest() {
        AddPayeeRequest response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/add_payee_request.json")) {
            response = objectMapper.readValue(in, AddPayeeRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    PayeeDetails toPayeeDetails(AddPayeeRequest request) {

        List<BankDetails> bankDetails = new ArrayList<>();
        request.getBankDetailsData().forEach(bankDetailsData -> bankDetails.add(toBankDetails(bankDetailsData)));

        var addressDetailsData = request.getAddressDetailsData();
        var taxAndPaymentData = request.getTaxAndPaymentData();

        return PayeeDetails.builder()
                .customerId(request.getCustomerId())
                .payeeName(request.getPayeeName())
                .upiID(request.getUpiID())
                .emailId(request.getEmailId())
                .isActive(true)
                .billingName(addressDetailsData.getBillingName())
                .addressLine1(addressDetailsData.getAddressLine1())
                .addressLine2(addressDetailsData.getAddressLine2())
                .pinCode(addressDetailsData.getPinCode())
                .city(addressDetailsData.getCity())
                .state(addressDetailsData.getState())
                .shippingName(addressDetailsData.getShippingName())
                .shippingAddressLine1(addressDetailsData.getShippingAddressLine1())
                .shippingAddressLine2(addressDetailsData.getShippingAddressLine2())
                .shippingPinCode(addressDetailsData.getShippingPinCode())
                .shippingCity(addressDetailsData.getShippingCity())
                .shippingState(addressDetailsData.getShippingState())
                .addressFlag(addressDetailsData.getAddressFlag())
                .mobileNumber(request.getMobileNumber())
                .gstNumber(taxAndPaymentData.getGstNumber())
                .panNumber(taxAndPaymentData.getPanNumber())
                .tanNumber(taxAndPaymentData.getTanNumber())
                .bankDetails(bankDetails)
                .build();
    }


    public BankDetails toBankDetails(BankDetailsData bankDetails) {
        return BankDetails.builder()
                .accountNumber(bankDetails.getAccountNumber())
                .accountHolderName(bankDetails.getAccountHolderName())
                .bankName(bankDetails.getBankName())
                .verificationFlag(bankDetails.getVerificationFlag())
                .ifscCode(bankDetails.getIfscCode())
                .build();
    }


    public AddPayeeRequest mockUpdatePayeeRequest() {
        AddPayeeRequest response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/update_payee_request.json")) {
            response = objectMapper.readValue(in, AddPayeeRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public AddPayeeRequest mockAddPayeeRequestWithOutAccount() {
        AddPayeeRequest response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/add_payee_without_account_request.json")) {
            response = objectMapper.readValue(in, AddPayeeRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public AddPayeeRequest mockAddPayeeRequestMoreAccount() {
        AddPayeeRequest response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/add_and_update_more_account_request.json")) {
            response = objectMapper.readValue(in, AddPayeeRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }


    public PayeeAccountValidationRequest mockPayeeAccountValidationRequest() {
        return PayeeAccountValidationRequest.builder().customerId(DUMMY_CUSTOMER_ID)
                .accountNumber(DUMMY_ACCOUNT_NUMBER).build();
    }

    public DeactivatePayeeRequest mockDeactivatePayeeRequest() {
        return DeactivatePayeeRequest.builder().customerId(DUMMY_CUSTOMER_ID).payeeName(DUMMY_PAYEE_NAME).build();
    }

}
