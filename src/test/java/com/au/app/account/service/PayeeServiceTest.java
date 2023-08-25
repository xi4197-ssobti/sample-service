package com.au.app.account.service;

import com.au.app.account.domain.entity.PayeeDetails;
import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.request.payee.DeactivatePayeeRequest;
import com.au.app.account.domain.request.payee.GetPayeeRequest;
import com.au.app.account.domain.request.payee.UpdatePayeeDetailsRequest;
import com.au.app.account.domain.request.payee_account_validation.PayeeAccountValidationRequest;
import com.au.app.account.domain.request.verify_payee_name.VerifyPayeeNameRequest;
import com.au.app.account.domain.response.VerifyResponse;
import com.au.app.account.domain.response.payee.PayeeDetailsResponse;
import com.au.app.account.repository.PayeeRepository;
import com.au.app.account.service.impl.PayeeServiceImpl;
import com.au.app.exception.AuBusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class PayeeServiceTest extends AbstractServiceTest {


    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        payeeRepository = Mockito.mock(PayeeRepository.class);
        payeeService = new PayeeServiceImpl(payeeRepository);
        objectMapper = new ObjectMapper();
    }

    @DisplayName("add payee happy case")
    @Test
    void addPayeeValidCase() {

        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        List<PayeeDetails> list = new ArrayList<>();
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(list);
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        Mockito.when(payeeRepository.save(payeeDetails)).thenReturn(payeeDetails);
        String response = payeeService.addPayee(addPayeeRequest);
        Assertions.assertEquals("Payee added successfully for cif = 123456712", response);

    }

    @DisplayName("add payee happy case with out account ")
    @Test
    void addPayeeValidCaseWithOutAccount() {

        AddPayeeRequest addPayeeRequest = mockAddPayeeRequestWithOutAccount();
        List<PayeeDetails> list = new ArrayList<>();
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(list);
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        Mockito.when(payeeRepository.save(payeeDetails)).thenReturn(payeeDetails);
        String response = payeeService.addPayee(addPayeeRequest);
        Assertions.assertEquals("Payee added successfully for cif = 123456712", response);

    }

    @DisplayName("add payee name already exist case")
    @Test
    void addPayeeErrorCaseNameExist() {
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(List.of(payeeDetails));
        AuBusinessException thrownAgain = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.addPayee(addPayeeRequest));
        Assertions.assertEquals("Payee with this name already exist, choose another name",
                thrownAgain.getMessage());

    }

    @DisplayName("add payee error case")
    @Test
    void addPayeeErrorCase() {

        AddPayeeRequest addPayeeRequest = mockAddPayeeRequestWithOutAccount();
        List<PayeeDetails> list = new ArrayList<>();
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(list);
        Mockito.when(payeeRepository.save(any())).thenThrow(AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.addPayee(addPayeeRequest));

        assertEquals("Failed adding payee", thrown.getMessage());

    }

    @DisplayName("add and update payee account limit exceed case")
    @Test
    void addAndUpdatePayeeAccountLimitErrorCase() {

        AddPayeeRequest addPayeeRequest = mockAddPayeeRequestMoreAccount();
        List<PayeeDetails> list = new ArrayList<>();
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(list);
        Mockito.when(payeeRepository.findById("1234567890")).thenReturn(Optional.ofNullable(null));
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.addPayee(addPayeeRequest));
        assertEquals("Account limit exceed", thrown.getMessage());
        UpdatePayeeDetailsRequest updatePayeeDetailsRequest = UpdatePayeeDetailsRequest.builder()
                .payeeId("1234567890").addPayeeRequest(addPayeeRequest).build();
        AuBusinessException thrownAgain = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.updatePayeeDetails(updatePayeeDetailsRequest));
        assertEquals("Account limit exceed", thrownAgain.getMessage());

    }

    @DisplayName("add payee account already exist case")
    @Test
    void addPayeeAccountExistCase() {
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        payeeDetails.setIsActive(true);
        payeeDetails.getBankDetails().forEach(bankDetails -> bankDetails.setActivationFlag(true));
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(List.of(payeeDetails));
        payeeDetails.setPayeeName("rishabh");
        Mockito.when(payeeRepository.save(payeeDetails)).thenReturn(payeeDetails);
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.addPayee(addPayeeRequest));
        assertEquals("This account number 190998 has already been added under payee name rishabh",
                thrown.getMessage());
    }

    @DisplayName("update payee no payee found")
    @Test
    void updatePayeeErrorCase() {

        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        Mockito.when(payeeRepository.findById("1234567890")).thenReturn(Optional.ofNullable(null));
        UpdatePayeeDetailsRequest updatePayeeDetailsRequest = UpdatePayeeDetailsRequest.builder()
                .payeeId("1234567890").addPayeeRequest(addPayeeRequest).build();
        AuBusinessException thrownAgain = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.updatePayeeDetails(updatePayeeDetailsRequest));
        assertEquals("No payee found for this payeeId: 1234567890", thrownAgain.getMessage());

    }

    @DisplayName("update payee account valid")
    @Test
    void updatePayeeValidCase() {
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();

        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        addPayeeRequest.setPayeeName("Shivam");
        payeeDetails.getBankDetails().forEach(bankDetails -> bankDetails.setActivationFlag(true));
        payeeDetails.getBankDetails().get(0).setAccountNumber("1234567890");
        Mockito.when(payeeRepository.findById("1234567890")).thenReturn(Optional.of(payeeDetails));
        Mockito.when(payeeRepository.save(payeeDetails)).thenReturn(payeeDetails);
        UpdatePayeeDetailsRequest updatePayeeDetailsRequest = UpdatePayeeDetailsRequest.builder()
                .payeeId("1234567890").addPayeeRequest(addPayeeRequest).build();
        String response = payeeService.updatePayeeDetails(updatePayeeDetailsRequest);
        assertEquals("payee updated successfully", response);
    }

    @DisplayName("get payee list ")
    @Test
    void getPayee() {
        GetPayeeRequest getPayeeRequest = GetPayeeRequest.builder().customerId("1234").build();
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        payeeDetails.getBankDetails().forEach(bankDetails -> bankDetails.setActivationFlag(true));

        Mockito.when(payeeRepository.findByCustomerId("1234")).thenReturn(List.of(payeeDetails));
        List<PayeeDetailsResponse> responses = payeeService.getPayee(getPayeeRequest);
        Assertions.assertNotNull(responses);
    }

    @DisplayName("verify payee name happy case")
    @Test
    void verifyPayeeNameValidCase() {
        List<PayeeDetails> payeeDetails = new ArrayList<>();
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(payeeDetails);
        VerifyPayeeNameRequest verifyPayeeNameRequest = VerifyPayeeNameRequest.builder()
                .payeeName("rishi yadav1").customerId("123").build();
        VerifyResponse response = payeeService.verifyPayeeName(verifyPayeeNameRequest);
        Assertions.assertEquals("you can add this payee name", response.getMessage());

    }

    @DisplayName("verify payee name error case")
    @Test
    void verifyPayeeNameErrorCase() {
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(List.of(payeeDetails));
        VerifyPayeeNameRequest verifyPayeeNameRequest = VerifyPayeeNameRequest.builder()
                .payeeName("rishi yadav1").customerId("123").build();
        VerifyResponse response = payeeService.verifyPayeeName(verifyPayeeNameRequest);
        Assertions.assertEquals("Payee with this name already exist, choose another name",
                response.getMessage());
        AuBusinessException thrownAgain = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.addPayee(addPayeeRequest));
        Assertions.assertEquals("Payee with this name already exist, choose another name",
                thrownAgain.getMessage());

    }

    @DisplayName("validate payee account happy case")
    @Test
    void validatePayeeAccountValidCase() {
        PayeeAccountValidationRequest addPayeeRequest = mockPayeeAccountValidationRequest();
        List<PayeeDetails> list = new ArrayList<>();
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(list);
        VerifyResponse response = payeeService.validatePayeeAccount(addPayeeRequest);
        Assertions.assertEquals("you can add this payee account", response.getMessage());
    }

    @DisplayName("validate payee account error case")
    @Test
    void validatePayeeAccountErrorCase() {
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        payeeDetails.setIsActive(true);
        payeeDetails.getBankDetails().forEach(bankDetails -> bankDetails.setActivationFlag(true));
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(List.of(payeeDetails));
        PayeeAccountValidationRequest payeeAccountValidationRequest = PayeeAccountValidationRequest.builder()
                .customerId("123456712").accountNumber("190998").build();

        VerifyResponse response = payeeService.validatePayeeAccount(payeeAccountValidationRequest);
        Assertions.assertEquals("This account number has already been added under payee name rishi yadav1",
                response.getMessage());
    }

    @DisplayName("deactivate payee no payee exist case")
    @Test
    void deactivatePayeeErrorCase() {
        DeactivatePayeeRequest deactivatePayeeRequest = mockDeactivatePayeeRequest();
        List<PayeeDetails> list = new ArrayList<>();
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(list);
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.deactivatePayee(deactivatePayeeRequest));
        assertEquals("No payee found for this customer 190998", thrown.getMessage());

    }

    @DisplayName("deactivate payee when payee not found case")
    @Test
    void deactivatePayeeError() {
        DeactivatePayeeRequest deactivatePayeeRequest = mockDeactivatePayeeRequest();
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(List.of(payeeDetails));
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeService.deactivatePayee(deactivatePayeeRequest));
        assertEquals("No payee found with this name RAVI", thrown.getMessage());

    }

    @DisplayName("deactivate payee happy case")
    @Test
    void deactivatePayee() {
        DeactivatePayeeRequest deactivatePayeeRequest = mockDeactivatePayeeRequest();
        deactivatePayeeRequest.setPayeeName("rishi yadav1");
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(List.of(payeeDetails));
        String response = payeeService.deactivatePayee(deactivatePayeeRequest);
        assertEquals("payee deactivated successfully", response);

    }


}
