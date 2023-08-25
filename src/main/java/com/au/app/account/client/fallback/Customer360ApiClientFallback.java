package com.au.app.account.client.fallback;

import com.au.app.account.client.Customer360ApiClient;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import java.util.concurrent.TimeoutException;

@Slf4j
@Component
public class Customer360ApiClientFallback implements FallbackFactory<Customer360ApiClient> {

    @Override
    public Customer360ApiClient create(Throwable cause) {
        log.error("Exception occurred for Customer360 API client and cause", cause);
        if (cause instanceof FeignException f) {
            int status = f.status();
            var errorMessage = f.contentUTF8();
            log.info("Feign customer Customer360 client status code = {} and errorMessage = {} ", status, errorMessage);
            throw new ResponseStatusException(HttpStatus.valueOf(status), errorMessage);
        } else if (cause instanceof TimeoutException) {
            throw new ResponseStatusException(HttpStatus.REQUEST_TIMEOUT,
                    "Request time-out due to bad connection or bad request");
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, cause.toString());
    }
}
