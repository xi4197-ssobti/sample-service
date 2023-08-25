package com.au.app.account.controller;

import com.au.app.account.domain.request.payee_accounts.PayeeAccountsRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PayeeCasaAccountsControllerTest extends AbstractRestControllerTests {

    @Test
    void test_get_payee_casa_accounts() throws Exception {
        PayeeAccountsRequest request = mockPayeeAccountsRequest();
        String content = objectMapper.writeValueAsString(request);

        this.mockMvc.perform(post("/api/account-service/payee-casa-accounts").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.status")
                .value("success")).andExpect(jsonPath(("$.successfulResponse")).value(true));
    }

    @Test
    void test_get_periodic_statement_exceptionally() throws Exception {
        PayeeAccountsRequest request = mockPayeeAccountsRequest();
        String content = objectMapper.writeValueAsString(request);
        doThrow(RuntimeException.class).when(payeeCasaAccountsService).getCasaAccountsfromMobileNo(any());

        this.mockMvc.perform(post("/api/account-service/payee-casa-accounts").content(content)
                                     .contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
                .andDo(print()).andExpect(status().isBadRequest()).andExpect(jsonPath("$.status")
                .value("error")).andExpect(jsonPath(("$.successfulResponse")).value(false))
                .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }
}
