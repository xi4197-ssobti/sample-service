package com.au.app.account.domain.response.customer_three_sixty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerAccount {
    @JsonProperty("ModuleCode")
    private String moduleCode;
    @JsonProperty("AccountId")
    private String accountId;
    @JsonProperty("CASAAccountName")
    private String cASAAccountName;
    @JsonProperty("CurrentStatus")
    private String currentStatus;
}
