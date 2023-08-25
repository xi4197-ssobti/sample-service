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
public class AddressDetailResponse {

    @JsonProperty("BillingName")
    private String billingName;
    @JsonProperty("AddressLine1")
    private String addressLine1;
    @JsonProperty("AddressLine2")
    private String addressLine2;
    @JsonProperty("PinCode")
    private String pinCode;
    @JsonProperty("City")
    private String city;
    @JsonProperty("State")
    private String state;
    @JsonProperty("ShippingName")
    private String shippingName;
    @JsonProperty("ShippingAddressLine1")
    private String shippingAddressLine1;
    @JsonProperty("ShippingAddressLine2")
    private String shippingAddressLine2;
    @JsonProperty("ShippingPinCode")
    private String shippingPinCode;
    @JsonProperty("ShippingCity")
    private String shippingCity;
    @JsonProperty("ShippingState")
    private String shippingState;
    @JsonProperty("AddressFlag")
    private Boolean addressFlag;

}
