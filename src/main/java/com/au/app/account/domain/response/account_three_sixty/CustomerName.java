package com.au.app.account.domain.response.account_three_sixty;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomerName {
    @JsonProperty("Prefix")
    private String prefix;
    @JsonProperty("FirstName")
    private String firstName;
    @JsonProperty("MidName")
    private String midName;
    @JsonProperty("LastName")
    private String lastName;
    @JsonProperty("ShortName")
    private String shortName;

}
