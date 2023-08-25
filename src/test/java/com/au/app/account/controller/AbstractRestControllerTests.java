package com.au.app.account.controller;

import com.au.app.account.domain.request.account_verification.AccountVerificationRequest;
import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsIntegrationRequest;
import com.au.app.account.domain.request.mini_statement.MiniStatementIntegrationRequest;
import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceIntegrationRequest;
import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.request.payee_account_validation.PayeeAccountValidationRequest;
import com.au.app.account.domain.request.payee_accounts.PayeeAccountsRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementIntegrationRequest;
import com.au.app.account.domain.request.verify_payee_name.VerifyPayeeNameRequest;
import com.au.app.account.service.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.io.InputStream;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
class AbstractRestControllerTests {

    static final String DUMMY_DATE = "01-May-2022";

    private static final String DUMMY_BLANK_DATA = "";
    private static final String DUMMY_NO_OF_TRANSACTIONS = "";
    static final String DUMMY_TRANSACTION_BRANCH = "2011";
    static final String DUMMY_ACCOUNT_NUMBER = "2201210944284781";

    static final String DUMMY_BENE_ACCOUNT_NUMBER = "2201210944284781";
    static final String DUMMY_BENE_MOBILE = "8787909898";
    static final String DUMMY_BENE_IFSC = "SBIN0032062";
    static final String DUMMY_REME_ACCOUNT_NUMBER = "2201210944284781";


    static final String DUMMY_REFERENCE_NUMBER = UUID.randomUUID().toString();
    static final String DUMMY_CHANNEL = "AUMB";
    private static final String DUMMY_CUSTOMER_ID = "23424387";
    private static final String DUMMY_PAYEE_NAME = "RAVI";

    static final String DUMMY_MOBILE_NUMBER = "8306833306";

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    MiniStatementService miniStatementService;

    @MockBean
    DebitCardDetailsService debitCardDetailsService;

    @MockBean
    MonthlyAccountBalanceService monthlyAccountBalanceService;

    @MockBean
    AccountVerificationService accountVerificationService;

    @MockBean
    Account360Service account360Service;
    @MockBean
    PayeeService payeeService;

    @MockBean
    PayeeCasaAccountsService payeeCasaAccountsService;


    public MiniStatementIntegrationRequest mockMiniStatementIntegrationRequest() {
        return MiniStatementIntegrationRequest.builder().accountID(DUMMY_ACCOUNT_NUMBER).referenceNumber(
                DUMMY_REFERENCE_NUMBER).requestId(DUMMY_REFERENCE_NUMBER).channel(DUMMY_CHANNEL).transactionBranch(
                DUMMY_TRANSACTION_BRANCH).pageSize(DUMMY_BLANK_DATA).pageNumber(DUMMY_BLANK_DATA).fromDate(
                DUMMY_BLANK_DATA).toDate(DUMMY_BLANK_DATA).noOftransactions(DUMMY_NO_OF_TRANSACTIONS).transactionDate(
                DUMMY_BLANK_DATA).build();
    }

    public DebitCardDetailsIntegrationRequest mockDebitCardDetailsIntegrationRequest() {
        return DebitCardDetailsIntegrationRequest.builder().requestId(DUMMY_REFERENCE_NUMBER).channel(DUMMY_CHANNEL)
                .customerId(DUMMY_CUSTOMER_ID).build();
    }

    public MonthlyAccountBalanceIntegrationRequest mockMonthlyAccountBalanceIntegrationRequest() {
        return MonthlyAccountBalanceIntegrationRequest.builder().accountId(DUMMY_ACCOUNT_NUMBER).requestId(
                        DUMMY_REFERENCE_NUMBER).transactionBranch(DUMMY_TRANSACTION_BRANCH)
                       .originatingChannel(DUMMY_CHANNEL)
                       .referenceNumber(DUMMY_REFERENCE_NUMBER).build();
    }

    public PeriodicStatementIntegrationRequest mockPeriodicStatementIntegrationRequest() {
        return PeriodicStatementIntegrationRequest.builder().startDate(DUMMY_DATE).requestId(DUMMY_REFERENCE_NUMBER)
                .originatingChannel(DUMMY_CHANNEL).referenceNumber(DUMMY_REFERENCE_NUMBER).transactionBranch(
                        DUMMY_TRANSACTION_BRANCH).endDate(DUMMY_DATE).accountNumber(DUMMY_ACCOUNT_NUMBER).build();
    }


    public VerifyPayeeNameRequest mockVerifyPayeeNameRequest() {
        return VerifyPayeeNameRequest.builder().customerId(DUMMY_CUSTOMER_ID).payeeName(DUMMY_PAYEE_NAME).build();
    }


    public PayeeAccountsRequest mockPayeeAccountsRequest() {
        return PayeeAccountsRequest.builder().mobileNumber(DUMMY_MOBILE_NUMBER).transactionBranch(
                DUMMY_TRANSACTION_BRANCH).build();
    }

    @DisplayName("test_abstract_rest_controller_tests")
    @Test
    void test_abstract_rest_controller_tests() {

        assertEquals("AUMB", mockMiniStatementIntegrationRequest().getChannel());


    }

    public AddPayeeRequest mockAddPayeeRequest() {
        AddPayeeRequest response = null;
        try (InputStream in = this.getClass().getClassLoader()
                                      .getResourceAsStream("json/add_payee_request.json")) {
            response = objectMapper.readValue(in, AddPayeeRequest.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return response;
    }

    public PayeeAccountValidationRequest mockPayeeAccountValidationRequest() {
        return PayeeAccountValidationRequest.builder().customerId(DUMMY_CUSTOMER_ID).accountNumber(DUMMY_ACCOUNT_NUMBER)
                .build();
    }


    public AccountVerificationRequest mockAccountVerificationRequest() {
        return AccountVerificationRequest.builder().transactionBranch(DUMMY_TRANSACTION_BRANCH)
                       .beneficiaryIFSCCode(DUMMY_BENE_IFSC)
                       .beneficiaryAccountNo(DUMMY_BENE_ACCOUNT_NUMBER)
                       .beneficiaryMobileNo(DUMMY_BENE_MOBILE)
                       .remitterAccountNo(DUMMY_REME_ACCOUNT_NUMBER).build();

    }

}
