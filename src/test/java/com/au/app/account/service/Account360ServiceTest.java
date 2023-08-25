package com.au.app.account.service;

import com.au.app.account.client.Account360ApiClient;
import com.au.app.account.domain.entity.PayeeDetails;
import com.au.app.account.domain.request.account_three_sixty.Account360IntegrationRequest;
import com.au.app.account.domain.request.account_three_sixty.Account360Request;
import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.response.account_three_sixty.Account360DetailResponse;
import com.au.app.account.service.impl.Account360ServiceImpl;
import com.au.app.exception.AuBusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class Account360ServiceTest extends AbstractServiceTest {
    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        account360ApiClient = Mockito.mock(Account360ApiClient.class);
        account360Service = new Account360ServiceImpl(payeeRepository,
                account360ApiClient, account360StagingRepository);
        objectMapper = new ObjectMapper();
    }

    @DisplayName("Account360 happy test")
    @Test
    void getAccount360ValidResponse() {

        Account360Request account360Request = Account360Request.builder()
                .accountNumber(DUMMY_ACCOUNT_NUMBER)
                .transactionBranch(DUMMY_BRANCH)
                .forAddPayee(false)
                .customerId(DUMMY_CUSTOMER_ID)
                .build();


        Account360IntegrationRequest account360EsbRequest = Account360IntegrationRequest.builder()
                .requestId(DUMMY_REQ_ID).channel(DUMMY_CHANNEL).accountNumber(account360Request.getAccountNumber())
                .transactionBranch(account360Request.getTransactionBranch()).referenceNumber(
                        DUMMY_REFERENCE_NUMBER).build();


        Mockito.when(account360ApiClient.getResponseFromAccount360ESB(any())).thenReturn(
                mockAccount360Response());


        assertNotNull(this.account360ApiClient.getResponseFromAccount360ESB(account360EsbRequest));
        assertEquals(16,
                this.account360ApiClient.getResponseFromAccount360ESB(account360EsbRequest).getData()
                        .getAccountDetail().get(0).getAccountId().length());
        Account360DetailResponse account360Response = account360Service.account360IntegrationDetails(account360Request);
        assertNotNull(account360Response);

    }

    @Test
    void GetAccount360ErrorResponseAccountExist() {

        Account360Request account360Request = Account360Request.builder()
                .accountNumber(DUMMY_ACCOUNT_NUMBER)
                .transactionBranch(DUMMY_BRANCH)
                .forAddPayee(true)
                .customerId(DUMMY_CUSTOMER_ID)
                .build();

        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        PayeeDetails payeeDetails = toPayeeDetails(addPayeeRequest);
        payeeDetails.setIsActive(true);
        payeeDetails.getBankDetails().forEach(bankDetails -> bankDetails.setActivationFlag(true));
        Mockito.when(payeeRepository.findByCustomerId(any())).thenReturn(List.of(payeeDetails));

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> account360Service.account360IntegrationDetails(account360Request));

        assertEquals("This account number has already been added under payee name rishi yadav1",
                thrown.getMessage());
    }

    @Test
    void GetAccount360ErrorResponse() {


        Mockito.when(account360ApiClient.getResponseFromAccount360ESB(any())).thenThrow(AuBusinessException.class);


        Account360Request account360Request = Account360Request.builder()
                .accountNumber(DUMMY_ACCOUNT_NUMBER)
                .transactionBranch(DUMMY_BRANCH)
                .forAddPayee(false)
                .customerId(DUMMY_CUSTOMER_ID)
                .build();

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> account360Service.account360IntegrationDetails(account360Request));

        assertEquals("Internal Server Error", thrown.getMessage());
    }

    @Test
    void GetAccount360Response_catch() {

        Account360Request account360Request = Account360Request.builder()
                .accountNumber(DUMMY_ACCOUNT_NUMBER)
                .transactionBranch(DUMMY_BRANCH)
                .forAddPayee(false)
                .customerId(DUMMY_CUSTOMER_ID)
                .build();
        Mockito.when(account360ApiClient.getResponseFromAccount360ESB(any())).thenThrow(
                AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> account360Service.account360IntegrationDetails(account360Request));
        assertEquals("Internal Server Error", thrown.getMessage());
    }


}
