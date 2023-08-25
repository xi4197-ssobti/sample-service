package com.au.app.account.domain.request.payee_account_validation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.NotBlank;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PayeeAccountValidationRequest {
    @NotBlank
    @JsonProperty(value = "CustomerId")
    private String customerId;
    @NotBlank
    @JsonProperty(value = "AccountNumber")
    private String accountNumber;
}
