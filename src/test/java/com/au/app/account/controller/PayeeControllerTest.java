package com.au.app.account.controller;

import com.au.app.account.domain.request.payee.AddPayeeRequest;
import com.au.app.account.domain.request.payee.DeactivatePayeeRequest;
import com.au.app.account.domain.request.payee.GetPayeeRequest;
import com.au.app.account.domain.request.payee.UpdatePayeeDetailsRequest;
import com.au.app.account.domain.request.payee_account_validation.PayeeAccountValidationRequest;
import com.au.app.account.domain.request.verify_payee_name.VerifyPayeeNameRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class PayeeControllerTest extends AbstractRestControllerTests {
    @Test
    void test_add_payee() throws Exception {
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        String content = objectMapper.writeValueAsString(addPayeeRequest);

        this.mockMvc.perform(
                        post("/api/account-service/add-payee").content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }


    @Test
    void test_add_payee_exceptionally() throws Exception {
        AddPayeeRequest addPayeeRequest = mockAddPayeeRequest();
        String content = objectMapper.writeValueAsString(addPayeeRequest);

        doThrow(RuntimeException.class).when(payeeService).addPayee(any());

        this.mockMvc.perform(
                        post("/api/account-service/add-payee")
                                .content(content).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath(("$.successfulResponse")).value(false))
                .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }

    @Test
    void test_get_payee_details() throws Exception {
        GetPayeeRequest getPayeeRequest = GetPayeeRequest.builder().customerId("1234").build();
        String content = objectMapper.writeValueAsString(getPayeeRequest);

        this.mockMvc.perform(
                        post("/api/account-service/get-payee").content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }

    @Test
    void test_get_payee_details_exceptionally() throws Exception {
        GetPayeeRequest getPayeeRequest = GetPayeeRequest.builder().customerId("1234").build();
        String content = objectMapper.writeValueAsString(getPayeeRequest);

        doThrow(RuntimeException.class).when(payeeService).getPayee(any());

        this.mockMvc.perform(
                        post("/api/account-service/get-payee")
                                .content(content).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath(("$.successfulResponse")).value(false))
                .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }

    @Test
    void test_verify_payee_name() throws Exception {
        VerifyPayeeNameRequest verifyPayeeNameRequest = mockVerifyPayeeNameRequest();
        String content = objectMapper.writeValueAsString(verifyPayeeNameRequest);

        this.mockMvc.perform(
                        post("/api/account-service/verify-payee-name").content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }


    @Test
    void test_get_verify_payee_name_exceptionally() throws Exception {
        VerifyPayeeNameRequest verifyPayeeNameRequest = mockVerifyPayeeNameRequest();
        String content = objectMapper.writeValueAsString(verifyPayeeNameRequest);

        doThrow(RuntimeException.class).when(payeeService).verifyPayeeName(any());

        this.mockMvc.perform(
                        post("/api/account-service/verify-payee-name")
                                .content(content).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath(("$.successfulResponse")).value(false))
                .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }

    @Test
    void test_validate_payee_account() throws Exception {
        PayeeAccountValidationRequest payeeAccountValidationRequest = mockPayeeAccountValidationRequest();
        String content = objectMapper.writeValueAsString(payeeAccountValidationRequest);

        this.mockMvc.perform(
                        post("/api/account-service/payee-account-validation").content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }


    @Test
    void test_get_validate_payee_account_exceptionally() throws Exception {
        PayeeAccountValidationRequest payeeAccountValidationRequest = mockPayeeAccountValidationRequest();
        String content = objectMapper.writeValueAsString(payeeAccountValidationRequest);

        doThrow(RuntimeException.class).when(payeeService).validatePayeeAccount(any());

        this.mockMvc.perform(
                        post("/api/account-service/payee-account-validation")
                                .content(content).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath(("$.successfulResponse")).value(false))
                .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }


    @Test
    void test_deactivate_payee() throws Exception {
        DeactivatePayeeRequest deactivatePayeeRequest = DeactivatePayeeRequest.builder().payeeName("nikhil")
                .customerId("12345").build();
        String content = objectMapper.writeValueAsString(deactivatePayeeRequest);

        this.mockMvc.perform(
                        post("/api/account-service/deactivate-payee").content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }

    @Test
    void test_deactivate_payee_exceptionally() throws Exception {
        DeactivatePayeeRequest deactivatePayeeRequest = DeactivatePayeeRequest.builder().payeeName("nikhil")
                .customerId("12345").build();
        String content = objectMapper.writeValueAsString(deactivatePayeeRequest);

        doThrow(RuntimeException.class).when(payeeService).deactivatePayee(any());

        this.mockMvc.perform(
                        post("/api/account-service/deactivate-payee")
                                .content(content).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath(("$.successfulResponse")).value(false))
                .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }

    @Test
    void test_update_payee() throws Exception {
        UpdatePayeeDetailsRequest updatePayeeDetailsRequest = UpdatePayeeDetailsRequest.builder()
                .addPayeeRequest(mockAddPayeeRequest())
                .payeeId("2345678iouytrew").build();
        String content = objectMapper.writeValueAsString(updatePayeeDetailsRequest);

        this.mockMvc.perform(
                        post("/api/account-service/update-payee-details").content(content)
                                .contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk()).andExpect(jsonPath("$.status").value("success"))
                .andExpect(jsonPath(("$.successfulResponse")).value(true));
    }

    @Test
    void test_update_payee_exceptionally() throws Exception {
        UpdatePayeeDetailsRequest updatePayeeDetailsRequest = UpdatePayeeDetailsRequest.builder()
                .addPayeeRequest(mockAddPayeeRequest())
                .payeeId("2345678iouytrew").build();
        String content = objectMapper.writeValueAsString(updatePayeeDetailsRequest);

        doThrow(RuntimeException.class).when(payeeService).updatePayeeDetails(any());

        this.mockMvc.perform(
                        post("/api/account-service/update-payee-details")
                                .content(content).contentType(MediaType.APPLICATION_JSON)
                                .accept(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value("error"))
                .andExpect(jsonPath(("$.successfulResponse")).value(false))
                .andExpect(jsonPath(("$.error.statusCode")).value("0"));
    }


}
