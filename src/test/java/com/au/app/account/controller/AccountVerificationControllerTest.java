package com.au.app.account.controller;

import com.au.app.account.domain.request.account_verification.AccountVerificationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AccountVerificationControllerTest extends AbstractRestControllerTests {
    @Test
    void test_imps_beneficiary_inquiry() throws Exception {
        AccountVerificationRequest accountVerificationRequest = mockAccountVerificationRequest();
        String content = objectMapper.writeValueAsString(accountVerificationRequest);

        this.mockMvc.perform(post("/api/account-service/account-verification").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success")).andExpect(
                jsonPath(("$.successfulResponse")).value(true));
    }

    @Test
    void test_imps_beneficiary_inquiry_exceptionally() throws Exception {
        AccountVerificationRequest accountVerificationRequest = mockAccountVerificationRequest();
        String content = objectMapper.writeValueAsString(accountVerificationRequest);

        doThrow(RuntimeException.class).when(accountVerificationService).getAccountVerificationService(any());

        this.mockMvc.perform(post("/api/account-service/account-verification").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("error")).andExpect(
                jsonPath(("$.successfulResponse")).value(false)).andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }
}
