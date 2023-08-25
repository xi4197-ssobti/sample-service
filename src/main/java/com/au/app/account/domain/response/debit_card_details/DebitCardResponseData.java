package com.au.app.account.domain.response.debit_card_details;

        import com.au.app.account.domain.response.transaction_status.TransactionStatus;
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
public class DebitCardResponseData {

    @JsonProperty("TransactionStatus")
    private TransactionStatus transactionStatus;
    @JsonProperty("CardDetails")
    private List<CardDetail> cardDetails = null;

}
