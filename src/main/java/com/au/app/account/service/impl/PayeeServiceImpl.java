package com.au.app.account.service.impl;

import com.au.app.account.domain.entity.BankDetails;
import com.au.app.account.domain.entity.PayeeDetails;
import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.request.payee.BankDetailsData;
import com.au.app.account.domain.request.payee.DeactivatePayeeRequest;
import com.au.app.account.domain.request.payee.GetPayeeRequest;
import com.au.app.account.domain.request.payee.UpdatePayeeDetailsRequest;
import com.au.app.account.domain.request.payee_account_validation.PayeeAccountValidationRequest;
import com.au.app.account.domain.request.verify_payee_name.VerifyPayeeNameRequest;
import com.au.app.account.domain.response.VerifyResponse;
import com.au.app.account.domain.response.payee.AddressDetailResponse;
import com.au.app.account.domain.response.payee.BankDetailsResponse;
import com.au.app.account.domain.response.payee.PayeeDetailsResponse;
import com.au.app.account.domain.response.payee.TaxAndPaymentResponse;
import com.au.app.account.repository.PayeeRepository;
import com.au.app.account.service.PayeeService;
import com.au.app.exception.AuBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.au.app.account.constant.AccountServiceConstants.MAX_ACCOUNT_ALLOWED;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0405;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0421;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0422;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0426;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0811;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0812;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.AS0813;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayeeServiceImpl implements PayeeService {

    private final PayeeRepository payeeRepository;

    @Override
    @Transactional
    public String addPayee(AddPayeeRequest request) {

        log.info("Inside add Payee, Request = {}", request);
        if (request.getBankDetailsData().size() > MAX_ACCOUNT_ALLOWED) {
            log.info("Payee is trying to add more then 3 accounts for customerId {}", request);
            throw new AuBusinessException(AS0813.getHttpStatus(), AS0813.getCode(), AS0813.getMessage());
        }

        //verifying payee name
        VerifyPayeeNameRequest verifyPayeeNameRequest = VerifyPayeeNameRequest.builder()
                .payeeName(request.getPayeeName()).customerId(request.getCustomerId()).build();
        VerifyResponse verifyResponseName = verifyPayeeName(verifyPayeeNameRequest);
        if (!verifyResponseName.isValidate()) {
            log.info("payee already exist with this name {} for customerId {}", request.getPayeeName(),
                    request.getCustomerId());
            throw new AuBusinessException(AS0811.getHttpStatus(), AS0811.getCode(), AS0811.getMessage());
        }

        //Verifying payee account
        request.getBankDetailsData().forEach(bankDetailsData -> {
            PayeeAccountValidationRequest accountValidationRequest = PayeeAccountValidationRequest.builder()
                    .accountNumber(bankDetailsData.getAccountNumber())
                    .customerId(request.getCustomerId()).build();
            VerifyResponse verifyResponseAccount = validatePayeeAccount(accountValidationRequest);
            if (!verifyResponseAccount.isValidate()) {
                log.info("This account number" + bankDetailsData.getAccountNumber()
                        + "has already been added under payee name " + verifyResponseAccount.getPayeeName()
                        + " {} ", request.getCustomerId());
                throw new AuBusinessException(AS0812.getHttpStatus(), AS0812.getCode(),
                        "This account number " + bankDetailsData.getAccountNumber()
                                + " has already been added under payee name " + verifyResponseAccount.getPayeeName());
            }
        });

        //collecting payee details to be saved
        PayeeDetails payeeDetails = toPayeeDetails(request);
        try {
            log.info("saving payee details {}", payeeDetails);
            payeeDetails.getBankDetails().forEach(bankDetails -> bankDetails.setPayeeDetails(payeeDetails));
            payeeRepository.save(payeeDetails);
            return "Payee added successfully for cif = " + request.getCustomerId();
        } catch (Exception e) {
            log.error("Error adding payee: {}", e.toString());
            throw new AuBusinessException(AS0405.getHttpStatus(), AS0405.getCode(), AS0405.getMessage());
        }
    }

    @Override
    public List<PayeeDetailsResponse> getPayee(GetPayeeRequest request) {
        log.info("getting payee details with request {}", request);
        List<PayeeDetails> payeeDetailsList = payeeRepository.findByCustomerId(request.getCustomerId());

        List<PayeeDetailsResponse> responseList = new ArrayList<>(); //return response
        payeeDetailsList.forEach(payeeDetails -> {
            TaxAndPaymentResponse taxAndPaymentResponse = toTaxAndPaymentResponse(payeeDetails);
            AddressDetailResponse addressDetailResponse = toAddressDetailResponse(payeeDetails);
            List<BankDetailsResponse> bankDetailsResponse = new ArrayList<>();
            payeeDetails.getBankDetails().forEach(bankDetails ->
                    bankDetailsResponse.add(toBankDetailsResponse(bankDetails)));

            PayeeDetailsResponse payeeDetailsResponse = PayeeDetailsResponse.builder()
                    .taxAndPaymentDetails(taxAndPaymentResponse).payeeId(payeeDetails.getId())
                    .addressDetails(addressDetailResponse).emailId(payeeDetails.getEmailId())
                    .bankDetails(bankDetailsResponse).mobileNumber(payeeDetails.getMobileNumber())
                    .payeeName(payeeDetails.getPayeeName()).upiId(payeeDetails.getUpiID())
                    .build();
            responseList.add(payeeDetailsResponse);

        });
        return responseList;
    }

    @Override
    public VerifyResponse verifyPayeeName(VerifyPayeeNameRequest request) {
        log.info("searching for payee with customer id {}", request.getCustomerId());
        List<PayeeDetails> details = payeeRepository.findByCustomerId(request.getCustomerId());
        VerifyResponse response = new VerifyResponse();

        //set response for default case
        response.setMessage("you can add this payee name");
        response.setValidate(true);
        response.setPayeeName("");

        if (!details.isEmpty()) {
            for (PayeeDetails payeeDetails : details) {
                if (payeeDetails.getPayeeName().equals(request.getPayeeName())) {
                    response.setMessage("Payee with this name already exist, choose another name");
                    response.setPayeeName(payeeDetails.getPayeeName());
                    response.setValidate(false);
                    return response;
                }
            }
        }
        return response;
    }

    @Override
    public VerifyResponse validatePayeeAccount(PayeeAccountValidationRequest request) {

        log.info("validate payee account for customer id {}", request.getCustomerId());
        List<PayeeDetails> payeeDetailsList = payeeRepository.findByCustomerId(request.getCustomerId());

        VerifyResponse response = new VerifyResponse();
        //set response for default case
        response.setMessage("you can add this payee account");
        response.setValidate(true);
        response.setPayeeName("");

        if (!payeeDetailsList.isEmpty()) payeeDetailsList.forEach(payeeDetails ->
                payeeDetails.getBankDetails().forEach(bankDetails -> {
                    if (bankDetails.getActivationFlag() && bankDetails.getAccountNumber()
                            .equals(request.getAccountNumber())) {
                        response.setMessage("This account number has already been added under payee name "
                                + payeeDetails.getPayeeName());
                        response.setValidate(false);
                        response.setPayeeName(payeeDetails.getPayeeName());
                    }
                }));
        return response;

    }

    @Override
    @Transactional
    public String deactivatePayee(DeactivatePayeeRequest deactivatePayeeRequest) {

        log.info("Inside deactivatePayee(), Request {}", deactivatePayeeRequest);
        List<PayeeDetails> payeeDetailsList = payeeRepository.findByCustomerId(deactivatePayeeRequest.getCustomerId());
        if (payeeDetailsList.isEmpty()) {
            log.info("No payee found for customerId {}", deactivatePayeeRequest.getCustomerId());
            throw new AuBusinessException(AS0421.getHttpStatus(), AS0421.getCode(), AS0421.getMessage()
                    + deactivatePayeeRequest.getCustomerId());
        } else {
            for (PayeeDetails payeeDetails : payeeDetailsList) {
                if (payeeDetails.getPayeeName().equals(deactivatePayeeRequest.getPayeeName())) {
                    payeeDetails.setIsActive(false);
                    payeeRepository.save(payeeDetails);
                    log.info("Payee deactivated successfully for customer-Id {}", deactivatePayeeRequest
                            .getCustomerId());
                    return "payee deactivated successfully";
                }
            }
        }
        log.info("No payee found with this name for customerId {}", deactivatePayeeRequest.getCustomerId());
        throw new AuBusinessException(AS0422.getHttpStatus(), AS0422.getCode(), AS0422.getMessage()
                + deactivatePayeeRequest.getPayeeName());
    }

    @Override
    @Transactional
    public String updatePayeeDetails(UpdatePayeeDetailsRequest request) {

        log.info("Inside update Payee, Request = {}", request);
        if (request.getAddPayeeRequest().getBankDetailsData().size() > MAX_ACCOUNT_ALLOWED) {
            log.info("Payee is trying to add more then 3 accounts");
            throw new AuBusinessException(AS0813.getHttpStatus(), AS0813.getCode(), AS0813.getMessage());
        }

        PayeeDetails payeeDetailList = payeeRepository.findById(request.getPayeeId())
                .orElseThrow(() -> new AuBusinessException(AS0426.getHttpStatus(), AS0426.getCode(),
                AS0426.getMessage() + request.getPayeeId()));

        if (!payeeDetailList.getCustomerId().equals(request.getAddPayeeRequest().getCustomerId())) {
            throw new AuBusinessException(AS0426.getHttpStatus(), AS0426.getCode(),
                    "request payee id and customer id does not match");
        } else if (payeeDetailList.getPayeeName().equals(request.getAddPayeeRequest().getPayeeName())
                || verifyPayeeName(getVerifyPayeeNameRequest(request)).isValidate()) {

            Map<String, BankDetailsData> requestAccountList = new HashMap<>();
            request.getAddPayeeRequest().getBankDetailsData().forEach(bankDetailsData ->
                    requestAccountList.put(bankDetailsData.getAccountNumber(), bankDetailsData)

            );

            List<BankDetails> bankDetailsList = new ArrayList<>();

            payeeDetailList.getBankDetails().forEach(bankDetails -> {
                if (requestAccountList.containsKey(bankDetails.getAccountNumber())) {
                    if (requestAccountList.get(bankDetails.getAccountNumber()).getIsAdded()) {
                        requestAccountList.remove(bankDetails.getAccountNumber());
                    } else {
//                        bankDetails.setActivationFlag(false);
                        BankDetails bankDetailsRequest = toBankDetails(requestAccountList
                                .get(bankDetails.getAccountNumber()));

                        bankDetails.setIfscCode(bankDetailsRequest.getIfscCode());
                        bankDetails.setAccountHolderName(bankDetailsRequest.getAccountHolderName());
                        bankDetails.setIsRtgsEnabled(bankDetailsRequest.getIsRtgsEnabled());
                        bankDetails.setIsNeftEnabled(bankDetailsRequest.getIsNeftEnabled());
                        bankDetails.setVerificationFlag(bankDetailsRequest.getVerificationFlag());

                        bankDetailsList.add(bankDetails);
                        //bankDetailsList.add(toBankDetails(requestAccountList.get(bankDetails.getAccountNumber())));
                        requestAccountList.remove(bankDetails.getAccountNumber());
                    }
                } else {
                    bankDetails.setActivationFlag(false);
                    bankDetailsList.add(bankDetails);
                }
            });

            request.getAddPayeeRequest().getBankDetailsData().forEach(bankDetailsData -> {
                if (requestAccountList.containsKey(bankDetailsData.getAccountNumber())) {
                    PayeeAccountValidationRequest accountValidationRequest = PayeeAccountValidationRequest.builder()
                            .accountNumber(bankDetailsData.getAccountNumber())
                            .customerId(request.getAddPayeeRequest().getCustomerId()).build();
                    VerifyResponse verifyResponse = validatePayeeAccount(accountValidationRequest);
                    if (verifyResponse.isValidate()) {
                        BankDetails bankDetails = toBankDetails(bankDetailsData);
                        bankDetailsList.add(bankDetails);
                    } else {
                        throw new AuBusinessException(AS0812.getHttpStatus(), AS0812.getCode(),
                                "This account number " + bankDetailsData.getAccountNumber()
                                        + " has already been added under payee name " + verifyResponse.getPayeeName());
                    }

                }
            });

            bankDetailsList.forEach(bankDetails -> bankDetails.setPayeeDetails(payeeDetailList));
            payeeDetailList.setBankDetails(bankDetailsList);

            payeeDetailList.setPayeeName(request.getAddPayeeRequest().getPayeeName());
            payeeDetailList.setMobileNumber(request.getAddPayeeRequest().getMobileNumber());
            payeeDetailList.setEmailId(request.getAddPayeeRequest().getEmailId());
            payeeDetailList.setUpiID(request.getAddPayeeRequest().getUpiID());
            var addressDetailsRequest = request.getAddPayeeRequest().getAddressDetailsData();
            payeeDetailList.setBillingName(addressDetailsRequest.getBillingName());
            payeeDetailList.setAddressLine1(addressDetailsRequest.getAddressLine1());
            payeeDetailList.setAddressLine2(addressDetailsRequest.getAddressLine2());
            payeeDetailList.setPinCode(addressDetailsRequest.getPinCode());
            payeeDetailList.setCity(addressDetailsRequest.getCity());
            payeeDetailList.setState(addressDetailsRequest.getState());
            payeeDetailList.setShippingName(addressDetailsRequest.getShippingName());
            payeeDetailList.setShippingAddressLine1(addressDetailsRequest.getShippingAddressLine1());
            payeeDetailList.setShippingAddressLine2(addressDetailsRequest.getShippingAddressLine2());
            payeeDetailList.setShippingPinCode(addressDetailsRequest.getShippingPinCode());
            payeeDetailList.setShippingCity(addressDetailsRequest.getShippingCity());
            payeeDetailList.setShippingState(addressDetailsRequest.getShippingState());
            payeeDetailList.setAddressFlag(addressDetailsRequest.getAddressFlag());

            var taxAndPaymentDetailsRequest = request.getAddPayeeRequest().getTaxAndPaymentData();

            payeeDetailList.setPanNumber(taxAndPaymentDetailsRequest.getPanNumber());
            payeeDetailList.setGstNumber(taxAndPaymentDetailsRequest.getGstNumber());
            payeeDetailList.setTanNumber(taxAndPaymentDetailsRequest.getTanNumber());

            payeeRepository.save(payeeDetailList);
            log.info("payee updated successfully for customerId {}", request.getAddPayeeRequest().getCustomerId());
            return "payee updated successfully";
        } else {
            throw new AuBusinessException(AS0811.getHttpStatus(), AS0811.getCode(),
                    AS0811.getMessage());
        }
    }

    private static VerifyPayeeNameRequest getVerifyPayeeNameRequest(UpdatePayeeDetailsRequest request) {
        return VerifyPayeeNameRequest.builder()
                .payeeName(request.getAddPayeeRequest().getPayeeName()).customerId(request.getAddPayeeRequest()
                        .getCustomerId()).build();
    }

    private PayeeDetails toPayeeDetails(AddPayeeRequest request) {

        List<BankDetails> bankDetails = new ArrayList<>();
        request.getBankDetailsData().forEach(bankDetailsData -> bankDetails.add(toBankDetails(bankDetailsData)));

        var addressDetailsData = request.getAddressDetailsData();
        var taxAndPaymentData = request.getTaxAndPaymentData();

        if (addressDetailsData.getAddressFlag()) {
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
                    .shippingName(addressDetailsData.getBillingName())
                    .shippingAddressLine1(addressDetailsData.getAddressLine1())
                    .shippingAddressLine2(addressDetailsData.getAddressLine2())
                    .shippingPinCode(addressDetailsData.getPinCode())
                    .shippingCity(addressDetailsData.getCity())
                    .shippingState(addressDetailsData.getState())
                    .addressFlag(addressDetailsData.getAddressFlag())
                    .mobileNumber(request.getMobileNumber())
                    .gstNumber(taxAndPaymentData.getGstNumber())
                    .panNumber(taxAndPaymentData.getPanNumber())
                    .tanNumber(taxAndPaymentData.getTanNumber())
                    .bankDetails(bankDetails)
                    .build();

        }

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


    private BankDetails toBankDetails(BankDetailsData bankDetails) {

        return BankDetails.builder()
                .accountNumber(bankDetails.getAccountNumber())
                .accountHolderName(bankDetails.getAccountHolderName())
                .bankName(bankDetails.getBankName())
                .verificationFlag(bankDetails.getVerificationFlag())
                .ifscCode(bankDetails.getIfscCode())
                .bankDisplayName(bankDetails.getBankDisplayName())
                .activationFlag(true)
                .isNeftEnabled(bankDetails.getIsNeftEnabled())
                .isRtgsEnabled(bankDetails.getIsRtgsEnabled())
                .build();
    }


    private TaxAndPaymentResponse toTaxAndPaymentResponse(PayeeDetails payeeDetails) {

        return TaxAndPaymentResponse.builder()
                .gstNumber(payeeDetails.getGstNumber())
                .panNumber(payeeDetails.getPanNumber())
                .tanNumber(payeeDetails.getTanNumber())
                .build();
    }

    private AddressDetailResponse toAddressDetailResponse(PayeeDetails payeeDetails) {

        return AddressDetailResponse.builder()
                .billingName(payeeDetails.getBillingName())
                .addressLine1(payeeDetails.getAddressLine1())
                .addressLine2(payeeDetails.getAddressLine2())
                .pinCode(payeeDetails.getPinCode())
                .city(payeeDetails.getCity())
                .state(payeeDetails.getState())
                .shippingName(payeeDetails.getShippingName())
                .shippingAddressLine1(payeeDetails.getShippingAddressLine1())
                .shippingAddressLine2(payeeDetails.getShippingAddressLine2())
                .shippingPinCode(payeeDetails.getShippingPinCode())
                .shippingCity(payeeDetails.getShippingCity())
                .shippingState(payeeDetails.getShippingState())
                .addressFlag(payeeDetails.getAddressFlag()).build();
    }

    private BankDetailsResponse toBankDetailsResponse(BankDetails bankDetails) {

        return BankDetailsResponse.builder()
                .bankName(bankDetails.getBankName())
                .accountNumber(bankDetails.getAccountNumber())
                .accountHolderName(bankDetails.getAccountHolderName())
                .ifscCode(bankDetails.getIfscCode()).verificationFlag(bankDetails.getVerificationFlag())
                .bankDisplayName(bankDetails.getBankDisplayName())
                .isAdded(true)
                .isNeftEnabled(bankDetails.getIsNeftEnabled())
                .isRtgsEnabled(bankDetails.getIsRtgsEnabled())
                .updatedAt(bankDetails.getLastModifiedAt())
                .build();
    }


}
