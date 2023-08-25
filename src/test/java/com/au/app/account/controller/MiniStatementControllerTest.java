package com.au.app.account.controller;

import com.au.app.account.domain.request.mini_statement.MiniStatementIntegrationRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementIntegrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;


import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MiniStatementControllerTest extends AbstractRestControllerTests {

    @Test
    void test_get_account_mini_statement() throws Exception {
        MiniStatementIntegrationRequest miniStatementIntegrationRequest = mockMiniStatementIntegrationRequest();
        String content = objectMapper.writeValueAsString(miniStatementIntegrationRequest);

        this.mockMvc.perform(post("/api/account-service/account-mini-statement").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success")).andExpect(
                jsonPath(("$.successfulResponse")).value(true));
    }

    @Test
    void test_get_monthly_account_balance_exceptionally() throws Exception {
        MiniStatementIntegrationRequest miniStatementIntegrationRequest = mockMiniStatementIntegrationRequest();
        String content = objectMapper.writeValueAsString(miniStatementIntegrationRequest);

        doThrow(RuntimeException.class).when(miniStatementService).getMiniStatementESB(any());

        this.mockMvc.perform(post("/api/account-service/account-mini-statement").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("error")).andExpect(
                jsonPath(("$.successfulResponse")).value(false)).andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }


    @Test
    void test_get_periodic_statement() throws Exception {
        PeriodicStatementIntegrationRequest periodicStatementIntegrationRequest = mockPeriodicStatementIntegrationRequest();
        String content = objectMapper.writeValueAsString(periodicStatementIntegrationRequest);

        this.mockMvc.perform(post("/api/account-service/account-periodic-statement").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success")).andExpect(
                jsonPath(("$.successfulResponse")).value(true));
    }

    @Test
    void test_get_periodic_statement_exceptionally() throws Exception {
        PeriodicStatementIntegrationRequest periodicStatementIntegrationRequest = mockPeriodicStatementIntegrationRequest();
        String content = objectMapper.writeValueAsString(periodicStatementIntegrationRequest);

        doThrow(RuntimeException.class).when(miniStatementService).getPeriodicStatementESB(any());

        this.mockMvc.perform(post("/api/account-service/account-periodic-statement").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(
                print()).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("error")).andExpect(
                jsonPath(("$.successfulResponse")).value(false)).andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }


}

