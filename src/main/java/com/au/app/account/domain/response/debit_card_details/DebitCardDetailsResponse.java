package com.au.app.account.domain.response.debit_card_details;

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
public class DebitCardDetailsResponse {

    @JsonProperty("error")
    private Object error;
    @JsonProperty("data")
    private DebitCardResponseData data;
    @JsonProperty("status")
    private String status;
    @JsonProperty("successfulResponse")
    private Boolean successfulResponse;



}
