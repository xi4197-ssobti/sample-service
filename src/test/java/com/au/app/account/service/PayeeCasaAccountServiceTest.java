package com.au.app.account.service;

import com.au.app.account.client.Customer360ApiClient;
import com.au.app.account.client.DedupeLiteApiClient;
import com.au.app.account.config.CurrentStatusProps;
import com.au.app.account.config.ModuleCodeProps;
import com.au.app.account.domain.request.payee_accounts.PayeeAccountsRequest;
import com.au.app.account.domain.response.customer_three_sixty.Customer360Response;
import com.au.app.account.domain.response.customer_three_sixty.CustomerAccount;
import com.au.app.account.domain.response.dedupe_lite.DedupeLiteResponseIntegration;
import com.au.app.account.service.impl.PayeeCasaAccountsServiceImpl;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class PayeeCasaAccountServiceTest {

    private static final String DUMMY_BRANCH = "2011";
    static final String DUMMY_MOBILE_NUMBER = "8306833306";
    @Mock
    private DedupeLiteApiClient dedupeLiteApiClient;
    @Mock
    private Customer360ApiClient customer360ApiClient;
    @Mock
    CurrentStatusProps currentStatusProps;
    @Mock
    ModuleCodeProps moduleCodeProps;

    private ObjectMapper objectMapper;

    private PayeeCasaAccountsService payeeCasaAccountsService;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        dedupeLiteApiClient = Mockito.mock(DedupeLiteApiClient.class);
        customer360ApiClient = Mockito.mock(Customer360ApiClient.class);
        payeeCasaAccountsService = new PayeeCasaAccountsServiceImpl(dedupeLiteApiClient, customer360ApiClient,
                currentStatusProps, moduleCodeProps);
        objectMapper = new ObjectMapper();
    }

    @DisplayName("PayeeCasaAccounts service happy test")
    @Test
    void getPayeeCasaAccountsValidResponse() {
        PayeeAccountsRequest payeeRequest = mockPayeeRequest();

        Mockito.when(this.dedupeLiteApiClient.getCasaAccounts(any())).thenReturn(mockDedupeIntegrationResponse());
        Mockito.when(this.customer360ApiClient.getCasaAccounts(any())).thenReturn(mockCustomer360IntegrationResponse());
        List<CustomerAccount> accounts = payeeCasaAccountsService.getCasaAccountsfromMobileNo(payeeRequest);
        assertNotNull(accounts);
    }

    @Test
    void getPayeeCasaAccountsDedupeErrorResponse() {
        PayeeAccountsRequest payeeRequest = mockPayeeRequest();
        Mockito.when(dedupeLiteApiClient.getCasaAccounts(any())).thenReturn(mockDedupeErrorResponse());

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeCasaAccountsService.getCasaAccountsfromMobileNo(payeeRequest));

        assertEquals("Transaction found with this  external reference number q32w2ww4e87d098ew423",
                thrown.getMessage());
    }

    //    @Test
    //    void getPayeeCasaAccountsCust360ErrorResponse() {
    //        PayeeAccountsRequest payeeRequest = mockPayeeRequest();
    //        Mockito.when(this.dedupeLiteApiClient.getCasaAccounts(any())).thenReturn(mockDedupeIntegrationResponse());
    //        Mockito.when(customer360ApiClient.getCasaAccounts(any())).thenReturn(mockCustomer360ErrorResponse());
    //
    //        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
    //                () -> payeeCasaAccountsService.getCasaAccountsfromMobileNo(payeeRequest));
    //
    //        assertEquals(
    //                "Transaction found with this  external reference number 23456543234567",
    //                thrown.getMessage());
    //    }

    @Test
    void getPayeeCasaAccountsResponse_dedupe_catch() {
        PayeeAccountsRequest payeeRequest = mockPayeeRequest();
        Mockito.when(dedupeLiteApiClient.getCasaAccounts(any())).thenThrow(AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeCasaAccountsService.getCasaAccountsfromMobileNo(payeeRequest));
        assertEquals("Internal Server Error", thrown.getMessage());
    }

    @Test
    void getPayeeCasaAccountsResponse_cust360_catch() {
        PayeeAccountsRequest payeeRequest = mockPayeeRequest();
        Mockito.when(this.dedupeLiteApiClient.getCasaAccounts(any())).thenReturn(mockDedupeIntegrationResponse());
        Mockito.when(this.customer360ApiClient.getCasaAccounts(any())).thenThrow(AuBusinessException.class);

        AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                () -> payeeCasaAccountsService.getCasaAccountsfromMobileNo(payeeRequest));
        assertEquals("Internal Server Error", thrown.getMessage());
    }

    private Customer360Response mockCustomer360IntegrationResponse() {
        Customer360Response response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/customer_360_mock_response.json")) {
            response = objectMapper.readValue(in, Customer360Response.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private Customer360Response mockCustomer360ErrorResponse() {
        Customer360Response response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/customer_360_error_response.json")) {
            response = objectMapper.readValue(in, Customer360Response.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private DedupeLiteResponseIntegration mockDedupeIntegrationResponse() {
        DedupeLiteResponseIntegration response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/dedupe_integration_mock_response.json")) {
            response = objectMapper.readValue(in, DedupeLiteResponseIntegration.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private DedupeLiteResponseIntegration mockDedupeErrorResponse() {
        DedupeLiteResponseIntegration response = null;
        try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                "json/dedupe_integration_error_response.json")) {
            response = objectMapper.readValue(in, DedupeLiteResponseIntegration.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    private PayeeAccountsRequest mockPayeeRequest() {
        return PayeeAccountsRequest.builder().mobileNumber(DUMMY_MOBILE_NUMBER).transactionBranch(DUMMY_BRANCH).build();
    }
}
