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
public class AddressDetailsData {

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
