package com.au.app.account.controller;

import com.au.app.account.domain.request.mini_statement.MiniStatementRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementRequest;
import com.au.app.account.domain.response.mini_statement.MiniStatementResponseData;
import com.au.app.account.domain.response.periodic_statement.PeriodicStatementResponseData;
import com.au.app.account.service.MiniStatementService;
import com.au.app.rest.commons.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Slf4j
@RequiredArgsConstructor
public class MiniStatementController {

    private final MiniStatementService miniStatementService;

    @PostMapping("/account-mini-statement")
    @Operation(description = "Mini Statement", summary = "Mini Statement of CASA Account using Account ID")
    public ApiResponse<MiniStatementResponseData> miniStatement(
            @Valid @RequestBody MiniStatementRequest miniStatementRequest) {
        log.info("Mini Statement Request received: {}", miniStatementRequest);
        return ApiResponse.success(miniStatementService.getMiniStatementESB(miniStatementRequest));
    }


    @PostMapping("/account-periodic-statement")
    @Operation(description = "Periodic Statement", summary = "Periodic Statement of CASA Account using Account ID")
    public ApiResponse<PeriodicStatementResponseData> periodicStatement(
            @Valid @RequestBody PeriodicStatementRequest periodicStatementRequest) {
        log.info("Periodic Statement Request received: {}", periodicStatementRequest);
        return ApiResponse.success(miniStatementService.getPeriodicStatementESB(periodicStatementRequest));
    }


}
