package com.au.app.account.controller;

import com.au.app.account.domain.request.account_three_sixty.Account360IntegrationRequest;
import com.au.app.account.domain.request.account_three_sixty.Account360Request;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountDetailsControllerTest extends AbstractRestControllerTests {
    private static final String DUMMY_REQ_ID = UUID.randomUUID().toString();
    private static final String DUMMY_CHANNEL = "AUMB";
    private static final String DUMMY_CUSTOMER_ID = "AUMB";

    @Test
    void test_get_account360_details() throws Exception {
        Account360Request account360Request = Account360Request.builder()
                .transactionBranch(DUMMY_TRANSACTION_BRANCH).accountNumber(DUMMY_ACCOUNT_NUMBER).forAddPayee(true)
                .customerId(DUMMY_CUSTOMER_ID).build();
        String content = objectMapper.writeValueAsString(account360Request);
        this.mockMvc.perform(post("/api/account-service/internal-account-verification").content(content)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isOk()).andExpect(jsonPath("$.status")
                .value("success")).andExpect(
                jsonPath(("$.successfulResponse")).value(true));


    }

    @Test
    void test_init_exceptionally() throws Exception {
        Account360Request account360Request = Account360Request.builder()
                .transactionBranch(DUMMY_TRANSACTION_BRANCH).accountNumber(DUMMY_ACCOUNT_NUMBER).forAddPayee(true)
                .customerId(DUMMY_CUSTOMER_ID).build();
        String content = objectMapper.writeValueAsString(account360Request);
        doThrow(RuntimeException.class).when(account360Service).account360IntegrationDetails(any());
        this.mockMvc.perform(post("/api/account-service/internal-account-verification").content(content)
                .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status")
                .value("error")).andExpect(
                jsonPath(("$.successfulResponse")).value(false));
    }

    protected Account360IntegrationRequest mockAccount360EsbRequest() {
        return Account360IntegrationRequest.builder().requestId(DUMMY_REQ_ID).channel(DUMMY_CHANNEL).accountNumber(
                DUMMY_ACCOUNT_NUMBER).transactionBranch(DUMMY_TRANSACTION_BRANCH).referenceNumber(
                DUMMY_REFERENCE_NUMBER).build();
    }

}
