package com.au.app.account.domain.request.verify_payee_name;

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
public class VerifyPayeeNameRequest {
    @NotBlank
    @JsonProperty(value = "CustomerId")
    private String customerId;
    @NotBlank
    @JsonProperty(value = "PayeeName")
    private String payeeName;
}
