package com.au.app.account.service;

import com.au.app.account.domain.request.mini_statement.MiniStatementRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementRequest;
import com.au.app.account.domain.response.mini_statement.MiniStatementResponseData;
import com.au.app.account.domain.response.periodic_statement.PeriodicStatementResponseData;


public interface MiniStatementService {
    MiniStatementResponseData getMiniStatementESB(MiniStatementRequest miniStatementRequest);

    PeriodicStatementResponseData getPeriodicStatementESB(PeriodicStatementRequest periodicStatementRequest);



}
