package com.au.app.account.domain.response.payee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxAndPaymentResponse {
    @JsonProperty("PANNumber")
    private String panNumber;
    @JsonProperty("TANNumber")
    private String tanNumber;
    @JsonProperty("GSTNumber")
    private String gstNumber;
}
