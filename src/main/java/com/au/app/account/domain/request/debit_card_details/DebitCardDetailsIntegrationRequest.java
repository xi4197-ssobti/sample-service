package com.au.app.account.domain.request.debit_card_details;


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
public class DebitCardDetailsIntegrationRequest {

    @JsonProperty(value = "RequestId")
    private String requestId;

    @JsonProperty(value = "Channel")
    private String channel;

    @JsonProperty(value = "CustomerId")
    private String customerId;
}
