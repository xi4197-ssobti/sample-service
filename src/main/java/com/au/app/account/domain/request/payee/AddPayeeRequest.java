package com.au.app.account.domain.request.payee;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@JsonSerialize
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AddPayeeRequest {
    @NotBlank
    @JsonProperty(value = "CustomerId")
    private String customerId;
    @NotBlank
    @JsonProperty(value = "PayeeName")
    private String payeeName;
    @JsonProperty(value = "MobileNumber")
    private String mobileNumber;
    @JsonProperty(value = "EmailId")
    private String emailId;
    @JsonProperty(value = "UpiID")
    private String upiID;
    @JsonProperty(value = "BankDetails")
    private List<BankDetailsData> bankDetailsData;
    @JsonProperty(value = "AddressDetails")
    private AddressDetailsData addressDetailsData;
    @JsonProperty(value = "TaxAndPayment")
    private TaxAndPaymentData taxAndPaymentData;

}
