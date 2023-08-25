package com.au.app.account.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_ACCEPTABLE;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.HttpStatus.SERVICE_UNAVAILABLE;

@AllArgsConstructor
@Getter
public enum ErrorExceptionCodes {

    AS0400("AS0400", "Invalid Request parameters.", BAD_REQUEST.value()),

    AS0500("AS0500", "Internal Server Error", INTERNAL_SERVER_ERROR.value()),

    AS0404("AS0404", "Data not found", NOT_FOUND.value()),
    AS0405("AS0405", "Failed adding payee", NOT_FOUND.value()),

    AS0423("AS0423", "Failed to connect to the downstream system. Please retry later.",
            SERVICE_UNAVAILABLE.value()),
    AS0420("AS0420", "Application signature can’t be matched", BAD_REQUEST.value()),
    AS0421("AS0421", "No payee found for this customer ", BAD_REQUEST.value()),
    AS0422("AS0422", "No payee found with this name ", BAD_REQUEST.value()),
    AS0424("AS0424", "Account is not associated with this payee ", BAD_REQUEST.value()),
    AS0425("AS0425", "No payee found for this account number ", BAD_REQUEST.value()),
    AS0426("AS0426", "No payee found for this payeeId: ", BAD_REQUEST.value()),
    AS0200("AS0200", "This account number has already been added under payee name ", OK.value()),

    AS0801("AS0801", "No product codes found for the customer type", NOT_FOUND.value()),

    AS0802("AS0802", "Bad request: customer type must not be null", BAD_REQUEST.value()),

    AS0803("AS0803", "Bad request: field codes must not be null", BAD_REQUEST.value()),

    AS0804("AS0804", "Bad request: field codes list must not be empty", BAD_REQUEST.value()),

    AS0805("AS0805", "Bad request: account id must not be null", BAD_REQUEST.value()),

    AS0806("AS0806", "Bad request: transaction branch must not be null", BAD_REQUEST.value()),

    AS0807("AS0807", "Internal server error, error from ESB: ", INTERNAL_SERVER_ERROR.value()),

    AS0808("AS0808", "Internal server error, null response from ESB", INTERNAL_SERVER_ERROR.value()),

    AS0809("AS0809", "Bad request, error from ESB: ", BAD_REQUEST.value()),

    AS0810("AS0810", "In complete records More than at a single day  ", NOT_ACCEPTABLE.value()),
    AS0811("AS0811", "Payee with this name already exist, choose another name", BAD_REQUEST.value()),
    AS0812("AS0812", "This account number has already been added under payee name ",
            BAD_REQUEST.value()),
    AS0813("AS0813", "Account limit exceed", BAD_REQUEST.value());


    private final String code;
    private final String message;
    private final int httpStatus;
}

