package com.au.app.account.domain.request.payee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class UpdatePayeeDetailsRequest {
    @NotBlank
    @JsonProperty(value = "PayeeId")
    private String payeeId;
    @JsonUnwrapped
    @Valid
    private AddPayeeRequest addPayeeRequest;
}
