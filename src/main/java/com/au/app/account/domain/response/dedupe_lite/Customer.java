package com.au.app.account.domain.response.dedupe_lite;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
public class Customer {

    @JsonProperty("CustomerID")
    private String customerID;

    @JsonProperty("MobileNumber")
    private String mobileNumber;

    @JsonProperty("BlockStatus")
    private String blockStatus;
}
