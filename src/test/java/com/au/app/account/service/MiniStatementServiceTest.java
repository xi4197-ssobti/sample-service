package com.au.app.account.service;

import com.au.app.account.client.MiniStatementApiClient;
import com.au.app.account.domain.request.mini_statement.MiniStatementIntegrationRequest;
import com.au.app.account.domain.request.mini_statement.MiniStatementRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementIntegrationRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementRequest;
import com.au.app.account.domain.response.mini_statement.MiniStatementResponse;
import com.au.app.account.domain.response.mini_statement.MiniStatementResponseData;
import com.au.app.account.domain.response.periodic_statement.PeriodicStatementResponse;
import com.au.app.account.domain.response.periodic_statement.PeriodicStatementResponseData;
import com.au.app.account.repository.MiniStatementStagingRepository;
import com.au.app.account.repository.PeriodicStatementStagingRepository;
import com.au.app.account.service.impl.MiniStatementServiceImpl;
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

class MiniStatementServiceTest {

    private static final String DUMMY_REQ_ID = UUID.randomUUID().toString();
    private static final String DUMMY_CHANNEL = "AUMB";
    private static final String DUMMY_BRANCH = "2011";
    private static final String DUMMY_ACCOUNT_ID = "2211213140025420";

    private static final String DUMMY_NO_OF_TRANSACTION = "5";
    public static final String DUMMY_NOT_REQUIRED = "";
    private static ObjectMapper objectMapper;

    private MiniStatementService miniStatementService;

    @Mock
    private MiniStatementApiClient miniStatementApiClient;

    @Mock
    private MiniStatementStagingRepository miniStatementStagingRepository;
    @Mock
    private PeriodicStatementStagingRepository periodicStatementStagingRepository;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        miniStatementApiClient = Mockito.mock(MiniStatementApiClient.class);
        miniStatementService = new MiniStatementServiceImpl(miniStatementApiClient, miniStatementStagingRepository,
                periodicStatementStagingRepository);
        objectMapper = new ObjectMapper();
    }

    @DisplayName("Mini Statement service happy test")
    @Test
    void getMiniStatementValidResponse() {

        MiniStatementRequest miniStatementRequest = getMiniStatementRequest();
        MiniStatementIntegrationRequest miniStatementIntegrationRequest =
                MiniStatementIntegrationRequest.builder().accountID(miniStatementRequest.getAccountID())
                        .referenceNumber(DUMMY_REQ_ID).requestId(DUMMY_REQ_ID)
                        .channel(DUMMY_CHANNEL).transactionBranch(
                                miniStatementRequest.getTransactionBranch()).pageSize(DUMMY_NOT_REQUIRED).pageNumber(
                                DUMMY_NOT_REQUIRED).fromDate(DUMMY_NOT_REQUIRED)
                        .toDate(DUMMY_NOT_REQUIRED).noOftransactions(miniStatementRequest.getNoOftransactions())
                        .transactionDate(DUMMY_NOT_REQUIRED).build();

        Mockito.when(miniStatementApiClient.getResponseFromMiniStatementESB(any())).thenReturn(
                mockMiniStatementSuccessResponse());

        assertNotNull(this.miniStatementApiClient.getResponseFromMiniStatementESB(miniStatementIntegrationRequest));
        assertEquals("0", this.miniStatementApiClient
                                  .getResponseFromMiniStatementESB(miniStatementIntegrationRequest)
                                  .getData().getTransactionStatus().getResponseCode());
        MiniStatementResponseData monthlyAccountBalanceResponse = miniStatementService.getMiniStatementESB(
                miniStatementRequest);
        assertNotNull(monthlyAccountBalanceResponse);

    }

    @Test
    void GetMiniStatementErrorResponse() {

        Mockito.when(miniStatementApiClient.getResponseFromMiniStatementESB(any())).thenReturn(
                mockMiniStatementErrorResponse());

        MiniStatementRequest miniStatementRequest = getMiniStatementRequest();
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> miniStatementService.getMiniStatementESB(miniStatementRequest));

        assertEquals("validations failed. 1", thrown.getMessage());
    }

    @Test
    void GetMiniStatementResponse_catch() {

        MiniStatementRequest miniStatementRequest = MiniStatementRequest.builder().accountID("12345").transactionBranch(
                DUMMY_BRANCH).noOftransactions(DUMMY_NO_OF_TRANSACTION).build();

        Mockito.when(miniStatementApiClient.getResponseFromMiniStatementESB(any())).thenThrow(
                AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> miniStatementService.getMiniStatementESB(miniStatementRequest));
        assertEquals("Internal Server Error", thrown.getMessage());
    }


    @DisplayName("Periodic Statement service happy test")
    @Test
    void getPeriodicStatementValidResponse() {

        PeriodicStatementRequest periodicStatementRequest = getPeriodicStatementRequest();
        PeriodicStatementIntegrationRequest periodicStatementIntegrationRequest =
                PeriodicStatementIntegrationRequest.builder().accountNumber(periodicStatementRequest.getAccountNumber())
                        .referenceNumber(DUMMY_REQ_ID).requestId(DUMMY_REQ_ID).originatingChannel(DUMMY_CHANNEL)
                        .transactionBranch(periodicStatementRequest.getTransactionBranch()).startDate(
                                periodicStatementRequest.getStartDate())
                        .endDate(periodicStatementRequest.getEndDate()).build();

        Mockito.when(miniStatementApiClient.getResponseFromPeriodicStatementESB(any())).thenReturn(
                mockPeriodicStatementSuccessResponse());

        assertNotNull(
                this.miniStatementApiClient.getResponseFromPeriodicStatementESB(periodicStatementIntegrationRequest));
        assertEquals("0", this.miniStatementApiClient.getResponseFromPeriodicStatementESB(
                periodicStatementIntegrationRequest).getData().getTransactionStatus().getResponseCode());
        PeriodicStatementResponseData periodicStatementResponseData = miniStatementService.getPeriodicStatementESB(
                periodicStatementRequest);
        assertNotNull(periodicStatementResponseData);

    }

    @DisplayName("Periodic Statement service happy test with more than 999 transaction")
    @Test
    void getPeriodicStatementValidResponseMoreTransaction() {

        PeriodicStatementRequest periodicStatementRequest = getPeriodicStatementRequest();
        PeriodicStatementIntegrationRequest periodicStatementIntegrationRequest =
                PeriodicStatementIntegrationRequest.builder().accountNumber(periodicStatementRequest.getAccountNumber())
                        .referenceNumber(DUMMY_REQ_ID).requestId(DUMMY_REQ_ID).originatingChannel(DUMMY_CHANNEL)
                        .transactionBranch(periodicStatementRequest.getTransactionBranch()).startDate(
                                periodicStatementRequest.getStartDate())
                        .endDate(periodicStatementRequest.getEndDate()).build();

        Mockito.when(miniStatementApiClient.getResponseFromPeriodicStatementESB(any())).thenReturn(
                mockPeriodicStatementSuccessResponse());

        assertNotNull(
                this.miniStatementApiClient.getResponseFromPeriodicStatementESB(periodicStatementIntegrationRequest));
        assertEquals("0", this.miniStatementApiClient.getResponseFromPeriodicStatementESB(
                periodicStatementIntegrationRequest).getData().getTransactionStatus().getResponseCode());
        PeriodicStatementResponseData periodicStatementResponseData = miniStatementService.getPeriodicStatementESB(
                periodicStatementRequest);
        assertNotNull(periodicStatementResponseData);

    }

    @DisplayName("Periodic Statement service test with more than 999 in a day throw error")
    @Test
    void getPeriodicStatementMoreErrorResponse() {

        PeriodicStatementRequest periodicStatementRequest = getPeriodicStatementRequest();

        Mockito.when(miniStatementApiClient.getResponseFromPeriodicStatementESB(any())).thenReturn(
                mockPeriodicStatement999Response());

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> miniStatementService.getPeriodicStatementESB(periodicStatementRequest));
        assertEquals("You have more than 999 transaction on 14-May-2022. Please visit nearest branch",
                thrown.getMessage());


    }

    @DisplayName("Periodic Statement service error response test")
    @Test
    void GetPeriodicStatementErrorResponse() {

        Mockito.when(miniStatementApiClient.getResponseFromPeriodicStatementESB(any())).thenReturn(
                mockPeriodicStatementErrorResponse());

        PeriodicStatementRequest periodicStatementRequest = getPeriodicStatementRequest();
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> miniStatementService.getPeriodicStatementESB(periodicStatementRequest));

        assertEquals("No record found", thrown.getMessage());
    }

    @DisplayName("Periodic Statement service error catch response test")
    @Test
    void getPeriodicStatementResponse_catch() {

        PeriodicStatementRequest periodicStatementRequest = getPeriodicStatementRequest();

        Mockito.when(miniStatementApiClient.getResponseFromPeriodicStatementESB(any())).thenThrow(
                AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> miniStatementService.getPeriodicStatementESB(periodicStatementRequest));
        assertEquals("Internal Server Error", thrown.getMessage());
    }


    private MiniStatementResponse mockMiniStatementSuccessResponse() {
        MiniStatementResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/mini_statement_mock_response.json")) {
            response = objectMapper.readValue(in, MiniStatementResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private PeriodicStatementResponse mockPeriodicStatementSuccessResponse() {
        PeriodicStatementResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/periodic_statement_mock_response.json")) {
            response = objectMapper.readValue(in, PeriodicStatementResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private MiniStatementResponse mockMiniStatementErrorResponse() {
        MiniStatementResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/mini_statement_error_response.json")) {
            response = objectMapper.readValue(in, MiniStatementResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private PeriodicStatementResponse mockPeriodicStatementErrorResponse() {
        PeriodicStatementResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/periodic_statement_error_response.json")) {
            response = objectMapper.readValue(in, PeriodicStatementResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private PeriodicStatementResponse mockPeriodicStatement999Response() {
        PeriodicStatementResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/periodic_statement_999_transaction.json")) {
            response = objectMapper.readValue(in, PeriodicStatementResponse.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private MiniStatementRequest getMiniStatementRequest() {
        return MiniStatementRequest.builder().accountID(DUMMY_ACCOUNT_ID).transactionBranch(DUMMY_BRANCH)
                       .noOftransactions(DUMMY_NO_OF_TRANSACTION).build();
    }

    private PeriodicStatementRequest getPeriodicStatementRequest() {
        return PeriodicStatementRequest.builder().accountNumber(DUMMY_ACCOUNT_ID).transactionBranch(DUMMY_BRANCH)
                       .startDate("12-may-2022").endDate("17-may-2022").build();
    }
}
