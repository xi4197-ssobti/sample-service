package com.au.app.account.service;

import com.au.app.account.client.DebitCardDetailsApiClient;
import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsIntegrationRequest;
import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsRequest;
import com.au.app.account.domain.response.debit_card_details.DebitCardDetailsResponse;
import com.au.app.account.domain.response.debit_card_details.DebitCardResponseData;
import com.au.app.account.repository.DebitCardDetailsRepository;
import com.au.app.account.service.impl.DebitCardDetailsServiceImpl;
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

class DebitCardDetailsServiceTest {

    private static final String DUMMY_REQ_ID = UUID.randomUUID().toString();
    private static final String DUMMY_CHANNEL = "AUMB";
    private static final String DUMMY_CUSTOMER_ID = "23424387";
    private static final String DUMMY_ACCOUNT_NO = "1971229924329930";


    private static ObjectMapper objectMapper;

    private DebitCardDetailsService debitCardDetailsService;

    @Mock
    private DebitCardDetailsApiClient debitCardDetailsApiClient;

    @Mock
    private DebitCardDetailsRepository debitCardDetailsRepository;

    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        debitCardDetailsApiClient = Mockito.mock(DebitCardDetailsApiClient.class);
        debitCardDetailsService = new DebitCardDetailsServiceImpl(debitCardDetailsApiClient,
                debitCardDetailsRepository);
        objectMapper = new ObjectMapper();
    }

    @DisplayName("DebitCardDetails service happy test")
    @Test
    void getDebitCardDetailsValidResponse() {

        DebitCardDetailsRequest debitCardDetailsRequest = mockDebitCardDetailsReq();
        DebitCardDetailsIntegrationRequest debitCardDetailsIntegrationRequest = DebitCardDetailsIntegrationRequest.builder().requestId(DUMMY_REQ_ID).channel(DUMMY_CHANNEL).customerId(
                DUMMY_CUSTOMER_ID).build();

        Mockito.when(debitCardDetailsApiClient.getDebitCardDetailsResponse(any())).thenReturn(
                mockDebitCardDetailsResponse());

        assertNotNull(this.debitCardDetailsApiClient.getDebitCardDetailsResponse(debitCardDetailsIntegrationRequest));
        assertEquals("0",
                this.debitCardDetailsApiClient.getDebitCardDetailsResponse(debitCardDetailsIntegrationRequest).getData().getTransactionStatus().getResponseCode());



        DebitCardResponseData debitCardResponseData =
                debitCardDetailsService.getDebitCardDetails(debitCardDetailsRequest);

        assertNotNull(debitCardResponseData);


    }





    @Test
    void GetDebitCardDetailsResponse_catch() {

        DebitCardDetailsRequest debitCardDetailsRequest = DebitCardDetailsRequest.builder().customerId("12345").build();

        Mockito.when(debitCardDetailsApiClient.getDebitCardDetailsResponse(any())).thenThrow(
                AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> debitCardDetailsService.getDebitCardDetails(debitCardDetailsRequest));
        assertEquals("Internal Server Error", thrown.getMessage());
    }

    @Test
    void GetDebitCardDetailsErrorResponse() {

        Mockito.when(debitCardDetailsApiClient.getDebitCardDetailsResponse(any())).thenReturn(
                mockDebitCardDetailsErrorResponse());

        DebitCardDetailsRequest debitCardDetailsRequest = mockDebitCardDetailsReq();
        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> debitCardDetailsService.getDebitCardDetails(debitCardDetailsRequest));

        assertEquals("Citizen id not found", thrown.getMessage());
    }


    private DebitCardDetailsResponse mockDebitCardDetailsResponse() {
        DebitCardDetailsResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/debit_card_details_response.json")) {
            response = objectMapper.readValue(in, DebitCardDetailsResponse.class);
        } catch (Exception e) {
        }
        return response;
    }

    private DebitCardDetailsResponse mockDebitCardDetailsErrorResponse() {
        DebitCardDetailsResponse response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/debit_card_details_error_response.json")) {
            response = objectMapper.readValue(in, DebitCardDetailsResponse.class);
        } catch (Exception e) {
        }
        return response;
    }


    private DebitCardDetailsRequest mockDebitCardDetailsReq() {
        return DebitCardDetailsRequest.builder().customerId(DUMMY_CUSTOMER_ID).accountNo(DUMMY_ACCOUNT_NO).build();
    }
}
