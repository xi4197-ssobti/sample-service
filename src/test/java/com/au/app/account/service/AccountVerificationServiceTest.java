package com.au.app.account.service;

import com.au.app.account.client.AccountVerificationClient;
import com.au.app.account.config.ESBHeaderProps;
import com.au.app.account.domain.request.account_verification.AccountVerificationRequest;
import com.au.app.account.domain.response.account_verification.AccountVerificationESBResponse;
import com.au.app.account.repository.AccountVerificationStagingRepository;
import com.au.app.account.service.impl.AccountVerificationServiceImpl;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class AccountVerificationServiceTest {

    private static ObjectMapper objectMapper;
    private AccountVerificationService accountVerificationService;
    @Mock
    private AccountVerificationClient accountVerificationClient;
    @Mock
    private AccountVerificationStagingRepository accountVerificationStagingRepository;

    @Mock
    private ESBHeaderProps headerProps;
    static final String DUMMY_TRANSACTION_BRANCH = "2011";

    static final String DUMMY_BENE_ACCOUNT_NUMBER = "2201210944284781";
    static final String DUMMY_BENE_MOBILE = "8787909898";
    static final String DUMMY_BENE_IFSC = "SBIN0032062";
    static final String DUMMY_REME_ACCOUNT_NUMBER = "2201210944284781";


    @BeforeEach
    void setup() {

        MockitoAnnotations.openMocks(this);
        accountVerificationClient = Mockito.mock(AccountVerificationClient.class);
        accountVerificationService = new AccountVerificationServiceImpl(headerProps, accountVerificationClient,
                accountVerificationStagingRepository);
        objectMapper = new ObjectMapper();
    }



     @DisplayName("IMPS-Account-Verification-Service")
     @Test
     void getAccountVerificationServiceValidResponse() {

          AccountVerificationRequest accountVerificationRequest = mockAccountVerificationRequest(DUMMY_BENE_ACCOUNT_NUMBER);

          Mockito.when(this.accountVerificationClient.getAccountVerificationESBResponse(any(), any())).thenReturn(
                  mockAccountVerificationESBResponse());
          AccountVerificationESBResponse impsBeneficiaryInquiryResponse = accountVerificationService.getAccountVerificationService(
                  accountVerificationRequest);
          assertNotNull(impsBeneficiaryInquiryResponse);
          assertEquals("Success", impsBeneficiaryInquiryResponse.getTransactionStatus().getResponseMessage());
     }


     @Test
     void getAccountVerificationServiceResponse_catch() {

          AccountVerificationRequest accountVerificationRequest = mockAccountVerificationRequest("1234");

          Mockito.when(this.accountVerificationClient.getAccountVerificationESBResponse(any(),any())).thenThrow(AuBusinessException.class);

          AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                  () -> accountVerificationService.getAccountVerificationService(accountVerificationRequest));
          assertEquals("Internal Server Error", thrown.getMessage());
     }

     @Test
     void getIAccountVerificationResponse_error() {

          AccountVerificationRequest accountVerificationRequest = mockAccountVerificationRequest("1234");

          AuBusinessException thrown = Assertions.assertThrows(AuBusinessException.class,
                  () -> accountVerificationService.getAccountVerificationService(accountVerificationRequest));
          assertEquals("Internal server error, null response from ESB", thrown.getMessage());
     }



     private AccountVerificationESBResponse mockAccountVerificationESBResponse() {
          AccountVerificationESBResponse response = null;
          try (InputStream in = this.getClass().getClassLoader().getResourceAsStream(
                  "json/account_verification_response.json")) {
               response = objectMapper.readValue(in, AccountVerificationESBResponse.class);
          } catch (Exception e) {
          }
          return response;
     }



    public AccountVerificationRequest mockAccountVerificationRequest(String BENE_ACCOUNT_NUMBER) {
        return AccountVerificationRequest.builder().transactionBranch(DUMMY_TRANSACTION_BRANCH).beneficiaryIFSCCode(
                        DUMMY_BENE_IFSC).beneficiaryAccountNo(BENE_ACCOUNT_NUMBER).beneficiaryMobileNo(DUMMY_BENE_MOBILE)
                       .remitterAccountNo(DUMMY_REME_ACCOUNT_NUMBER).build();

    }

}
