package com.au.app.account.domain.response.customer_three_sixty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class AccountDetails {

    @JsonProperty("CustomerAccount")
    private List<CustomerAccount> customerAccount;
    @JsonProperty("IsCustomerSchemeAvailable")
    private Boolean isCustomerSchemeAvailable;


}
