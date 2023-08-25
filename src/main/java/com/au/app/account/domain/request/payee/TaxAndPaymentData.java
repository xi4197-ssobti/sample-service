package com.au.app.account.domain.request.payee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class TaxAndPaymentData {
    @JsonProperty("PANNumber")
    private String panNumber;
    @JsonProperty("TANNumber")
    private String tanNumber;
    @JsonProperty("GSTNumber")
    private String gstNumber;
}


