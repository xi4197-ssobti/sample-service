package com.au.app.account.controller;

import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceIntegrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class MonthlyAccountBalanceControllerTest extends AbstractRestControllerTests {

    @Test void test_get_monthly_account_balance() throws Exception {
        MonthlyAccountBalanceIntegrationRequest debitCardDetailsESBRequest = mockMonthlyAccountBalanceIntegrationRequest();
        String content = objectMapper.writeValueAsString(debitCardDetailsESBRequest);

        this.mockMvc.perform(post("/api/account-service/monthly-account-balance").content(content).contentType(
                    MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                    .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }

    @Test void test_get_monthly_account_balance_exceptionally() throws Exception {
        MonthlyAccountBalanceIntegrationRequest debitCardDetailsESBRequest = mockMonthlyAccountBalanceIntegrationRequest();
        String content = objectMapper.writeValueAsString(debitCardDetailsESBRequest);

        doThrow(RuntimeException.class).when(monthlyAccountBalanceService).getMonthlyAccountBalanceESB(any());

        this.mockMvc.perform(post("/api/account-service/monthly-account-balance").content(content).contentType(
                    MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON)).andDo(print())
                    .andExpect(status().isBadRequest()).andExpect(jsonPath("$.status").value("error"))
                    .andExpect(jsonPath(("$.successfulResponse")).value(false))
                    .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }


}

