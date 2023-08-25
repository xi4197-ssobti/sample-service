package com.au.app.account.domain.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class VerifyResponse {
    private boolean validate;
    private String message;
    private String payeeName;
}
