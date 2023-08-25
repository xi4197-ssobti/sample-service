package com.au.app.account.service;

import com.au.app.account.client.MonthlyAccountBalanceApiClient;
import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceIntegrationRequest;
import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceRequest;
import com.au.app.account.domain.response.monthly_account_balance.MonthlyAccountBalanceResponse;
import com.au.app.account.dto.monthly_account_balance.MonthlyAccountBalanceDataDto;
import com.au.app.account.repository.MonthlyAccountBalanceStagingRepository;
import com.au.app.account.service.impl.MonthlyAccountBalanceServiceImpl;
import com.au.app.exception.AuBusinessException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

class MonthlyAccountBalanceServiceTest {

    private static final String DUMMY_REQ_ID = UUID.randomUUID().toString();
    private static final String DUMMY_CHANNEL = "AUMB";
    private static final String DUMMY_BRANCH = "2011";

    private static final String DUMMY_ACCOUNT_ID = "2211213140025420";


    private static ObjectMapper objectMapper;

    private MonthlyAccountBalanceService monthlyAccountBalanceService;

    @Mock
    private MonthlyAccountBalanceApiClient monthlyAccountBalanceApiClient;

    @Mock
    private MonthlyAccountBalanceStagingRepository monthlyAccountBalanceStagingRepository;


    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        monthlyAccountBalanceApiClient = Mockito.mock(MonthlyAccountBalanceApiClient.class);
        monthlyAccountBalanceService = new MonthlyAccountBalanceServiceImpl(monthlyAccountBalanceApiClient,
                monthlyAccountBalanceStagingRepository);
        objectMapper = new ObjectMapper();
    }

    @DisplayName("MonthlyAccountBalance service happy test")
    @Test
    void getMonthlyAccountBalanceValidResponse() {

        MonthlyAccountBalanceRequest monthlyAccountBalanceRequest = getMonthlyAccountBalanceRequest();
        MonthlyAccountBalanceIntegrationRequest monthlyAccountBalanceESBRequest =
                MonthlyAccountBalanceIntegrationRequest.builder().requestId(DUMMY_REQ_ID).originatingChannel(DUMMY_CHANNEL)
                        .accountId(monthlyAccountBalanceRequest.getAccountId()).transactionBranch(
                                monthlyAccountBalanceRequest.getTransactionBranch()).referenceNumber(DUMMY_REQ_ID)
                        .build();

        Mockito.when(monthlyAccountBalanceApiClient.monthlyAccountBalance(any())).thenReturn(
                mockMonthlyAccountBalanceResponse());

        assertNotNull(this.monthlyAccountBalanceApiClient.monthlyAccountBalance(monthlyAccountBalanceESBRequest));
        assertEquals("0", this.monthlyAccountBalanceApiClient.monthlyAccountBalance(monthlyAccountBalanceESBRequest).getData().getTransactionStatus().getExtendedErrorDetails()
                             .getMessages().get(0)
                             .getCode());
        MonthlyAccountBalanceDataDto monthlyAccountBalanceResponse =
                monthlyAccountBalanceService.getMonthlyAccountBalanceESB(monthlyAccountBalanceRequest);
        assertNotNull(monthlyAccountBalanceResponse);

    }


    @Test
    void GetMonthlyAccountBalanceErrorResponse() {

        Mockito.when(monthlyAccountBalanceApiClient.monthlyAccountBalance(any())).thenReturn(
                mockMonthlyAccountBalanceErrorResponse());

        MonthlyAccountBalanceRequest monthlyAccountBalanceRequest = getMonthlyAccountBalanceRequest();
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> monthlyAccountBalanceService.getMonthlyAccountBalanceESB(monthlyAccountBalanceRequest));

        assertEquals("Record not found", thrown.getMessage());
    }

    @Test
    void GetMonthlyAccountBalanceResponse_catch() {

        MonthlyAccountBalanceRequest monthlyAccountBalanceRequest = MonthlyAccountBalanceRequest.builder().accountId(
                "12345").transactionBranch(DUMMY_BRANCH).build();
        Mockito.when(monthlyAccountBalanceApiClient.monthlyAccountBalance(any())).thenThrow(
                AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> monthlyAccountBalanceService.getMonthlyAccountBalanceESB(monthlyAccountBalanceRequest));
        assertEquals("Internal Server Error", thrown.getMessage());
    }


    private MonthlyAccountBalanceResponse mockMonthlyAccountBalanceResponse() {
        MonthlyAccountBalanceResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/montly_account_balance_mock_response.json")) {
            response = objectMapper.readValue(in, MonthlyAccountBalanceResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private MonthlyAccountBalanceResponse mockMonthlyAccountBalanceErrorResponse() {
        MonthlyAccountBalanceResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/montly_account_balance_error_mock_response.json")) {
            response = objectMapper.readValue(in, MonthlyAccountBalanceResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private MonthlyAccountBalanceRequest getMonthlyAccountBalanceRequest() {
        return MonthlyAccountBalanceRequest.builder().accountId(DUMMY_ACCOUNT_ID).transactionBranch(DUMMY_BRANCH)
                       .build();
    }
}
