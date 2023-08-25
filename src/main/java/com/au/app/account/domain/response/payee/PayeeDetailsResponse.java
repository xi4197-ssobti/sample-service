package com.au.app.account.domain.response.payee;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PayeeDetailsResponse {
    @JsonProperty("PayeeId")
    private String payeeId;
    @JsonProperty("PayeeName")
    private String payeeName;
    @JsonProperty("MobileNumber")
    private String mobileNumber;
    @JsonProperty("UpiId")
    private String upiId;
    @JsonProperty("EmailId")
    private String emailId;
    @JsonProperty("BankDetails")
    private List<BankDetailsResponse> bankDetails;
    @JsonProperty("AddressDetails")
    private AddressDetailResponse addressDetails;
    @JsonProperty("TaxAndPaymentDetails")
    private TaxAndPaymentResponse taxAndPaymentDetails;

}
