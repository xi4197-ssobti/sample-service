package com.au.app.account.exception;

import com.au.app.exception.AuApplicationException;
import com.au.app.exception.AuBusinessException;
import com.au.app.rest.commons.ApiResponse;
import com.au.app.rest.commons.ErrorResponse;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import feign.FeignException;
import feign.Request;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.Arrays;
import java.util.Collections;

@ControllerAdvice
@ResponseBody
@Slf4j
public class APIExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("Invalid request parameters", e);
        return ApiResponse.error(new ErrorResponse(HttpStatus.BAD_REQUEST.value(), "Invalid Parameters",
                e.getBindingResult().getFieldErrors().stream()
                        .map(fieldError -> fieldError.getField() + " " + fieldError.getDefaultMessage()).toList()));
    }

    @ExceptionHandler(value = {ResponseStatusException.class})
    @ResponseStatus(value = HttpStatus.OK)
    public ApiResponse<Void> handleResponseStatusException(ResponseStatusException ex) {
        return ApiResponse.error(new ErrorResponse(ex.getStatus().value(), ex.getReason()));
    }

    @ExceptionHandler(FeignException.class)
    public ApiResponse<ErrorResponse> handleFeignException(FeignException e) {
        Request request = e.request();
        String url = request.url();
        String httpMethod = request.httpMethod().name();
        String body = new String(request.body());
        log.error("Failed to make {} {} with request body {} with exception {} ", httpMethod, url, body, e);
        return ApiResponse.error(
                new ErrorResponse(e.status(), "Failed to connect to the downstream system. Please retry later."));
    }

    @ExceptionHandler(AuBusinessException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleBusinessException(AuBusinessException e) {
        ErrorResponse response = new ErrorResponse(e.getCode(), e.getErrorCode(), e.getMessage());
        return ApiResponse.error(response);
    }

    @ExceptionHandler(AuApplicationException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleApplicationException(AuApplicationException e) {
        ErrorResponse response = new ErrorResponse(e.getCode(), e.getErrorCode(), e.getMessage());
        return ApiResponse.error(response);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ApiResponse<ErrorResponse> handleException(Exception e) {
        log.error("Exception: ", e);
        if (e.getCause() instanceof InvalidFormatException ifx && ifx.getTargetType() != null && ifx.getTargetType()
                                                                                                         .isEnum()) {
            var errorDetails = String.format(
                    "Invalid enum value: '%s' for the field: '%s'. " + "The value must be one of: %s.", ifx.getValue(),
                    ifx.getPath().get(ifx.getPath().size() - 1).getFieldName(),
                    Arrays.toString(ifx.getTargetType().getEnumConstants()));

            ErrorResponse response = new ErrorResponse(HttpStatus.BAD_REQUEST.value(), null, "Invalid Request",
                    Collections.singletonList(errorDetails));
            return ApiResponse.error(response);
        }
        ErrorResponse response = new ErrorResponse("500", "Server internal error");
        return ApiResponse.error(response);
    }
}
