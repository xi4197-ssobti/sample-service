package com.au.app.account.controller;

import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsIntegrationRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class DebitCardControllerTests extends AbstractRestControllerTests {


    @Test void test_get_debit_card_details() throws Exception {
        DebitCardDetailsIntegrationRequest debitCardDetailsIntegrationRequest = mockDebitCardDetailsIntegrationRequest();
        String content = objectMapper.writeValueAsString(debitCardDetailsIntegrationRequest);

        this.mockMvc.perform(
                    post("/api/account-service/debit-card-details").content(content)
                                                                   .contentType(MediaType.APPLICATION_JSON)
                                                                   .accept(MediaType.APPLICATION_JSON)).andDo(print())
                    .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                    .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }


    @Test void test_get_debit_card_details_exceptionally() throws Exception {
        DebitCardDetailsIntegrationRequest debitCardDetailsIntegrationRequest = mockDebitCardDetailsIntegrationRequest();
        String content = objectMapper.writeValueAsString(debitCardDetailsIntegrationRequest);

        doThrow(RuntimeException.class).when(debitCardDetailsService).getDebitCardDetails(any());

        this.mockMvc.perform(
                    post("/api/account-service/debit-card-details")
                            .content(content).contentType(MediaType.APPLICATION_JSON)
                            .accept(MediaType.APPLICATION_JSON)).andDo(print())
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value("error"))
                    .andExpect(jsonPath(("$.successfulResponse")).value(false))
                    .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }


}
