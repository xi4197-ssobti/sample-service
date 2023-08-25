package com.au.app.account.controller;

import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.request.payee.DeactivatePayeeRequest;
import com.au.app.account.domain.request.payee.GetPayeeRequest;
import com.au.app.account.domain.request.payee.UpdatePayeeDetailsRequest;
import com.au.app.account.domain.request.payee_account_validation.PayeeAccountValidationRequest;
import com.au.app.account.domain.request.verify_payee_name.VerifyPayeeNameRequest;
import com.au.app.account.domain.response.VerifyResponse;
import com.au.app.account.domain.response.payee.PayeeDetailsResponse;
import com.au.app.account.service.PayeeService;
import com.au.app.rest.commons.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
public class PayeeController {
    private final PayeeService payeeService;

    @PostMapping("/add-payee")
    @Operation(description = "add payee ",
            summary = "add payee/beneficiary for a customer")
    public ApiResponse<String> addPayee(
            @Valid @RequestBody AddPayeeRequest addPayeeRequest) {
        log.info("adding payee with request: {}", addPayeeRequest);
        return ApiResponse.success(payeeService.addPayee(addPayeeRequest));
    }

    @PostMapping("/get-payee")
    @Operation(description = "add payee ",
            summary = "get all payee/beneficiary for a customer")
    public ApiResponse<List<PayeeDetailsResponse>> getPayee(
            @Valid @RequestBody GetPayeeRequest getPayeeRequest) {
        log.info("getting all payee list for request: {}", getPayeeRequest);
        return ApiResponse.success(payeeService.getPayee(getPayeeRequest));
    }

    @PostMapping("/verify-payee-name")
    @Operation(description = "verify name match ",
            summary = "verify name for add payee")
    public ApiResponse<VerifyResponse> verifyPayeeName(
            @Valid @RequestBody VerifyPayeeNameRequest verifyPayeeNameRequest) {
        log.info("verify payee name with request: {}", verifyPayeeNameRequest);
        return ApiResponse.success(payeeService.verifyPayeeName(verifyPayeeNameRequest));
    }

    @PostMapping("/payee-account-validation")
    @Operation(description = "Validate Payee Account",
            summary = "Validate Payee Account for add payee")
    public ApiResponse<VerifyResponse> validatePayeeAccount(
            @Valid @RequestBody PayeeAccountValidationRequest payeeAccountValidationRequest) {
        log.info("Validate Payee Account with request: {}", payeeAccountValidationRequest);
        return ApiResponse.success(payeeService.validatePayeeAccount(payeeAccountValidationRequest));
    }

    @PostMapping("/deactivate-payee")
    @Operation(description = "deactivate Payee ",
            summary = "deactivate Payee for an customer")
    public ApiResponse<String> deactivatePayee(
            @Valid @RequestBody DeactivatePayeeRequest deactivatePayeeRequest) {
        log.info("deactivate Payee for an Account with request: {}", deactivatePayeeRequest);
        return ApiResponse.success(payeeService.deactivatePayee(deactivatePayeeRequest));
    }

    @PostMapping("/update-payee-details")
    @Operation(description = "Update Payee Details",
            summary = "update payee account for an customer")
    public ApiResponse<String> updatePayeeDetails(
            @Valid @RequestBody UpdatePayeeDetailsRequest updatePayeeDetailsRequest) {
        log.info("update Payee details with request: {}", updatePayeeDetailsRequest);
        return ApiResponse.success(payeeService.updatePayeeDetails(updatePayeeDetailsRequest));
    }

}


