package com.au.app.account.service.impl;

import com.au.app.account.client.MonthlyAccountBalanceApiClient;
import com.au.app.account.domain.entity.MonthlyAccountBalanceStaging;
import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceIntegrationRequest;
import com.au.app.account.domain.request.monthly_account_balance.MonthlyAccountBalanceRequest;
import com.au.app.account.domain.response.monthly_account_balance.BalanceDetail;
import com.au.app.account.domain.response.monthly_account_balance.MonthlyAccountBalanceResponse;
import com.au.app.account.dto.monthly_account_balance.BalanceDetailDto;
import com.au.app.account.dto.monthly_account_balance.MonthlyAccountBalanceDataDto;
import com.au.app.account.repository.MonthlyAccountBalanceStagingRepository;
import com.au.app.account.service.MonthlyAccountBalanceService;
import com.au.app.account.util.JsonUtil;
import com.au.app.exception.AuBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.*;

import static com.au.app.account.constant.AccountServiceConstants.*;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.*;
import static com.au.app.account.util.JsonUtil.getJsonString;

@Service
@Slf4j
@RequiredArgsConstructor
public class MonthlyAccountBalanceServiceImpl implements MonthlyAccountBalanceService {
    private final MonthlyAccountBalanceApiClient monthlyAccountBalanceApiClient;
    private final MonthlyAccountBalanceStagingRepository monthlyAccountBalanceStagingRepository;

    @Override
    public MonthlyAccountBalanceDataDto getMonthlyAccountBalanceESB(
            MonthlyAccountBalanceRequest monthlyAccountBalanceRequest) {

        final String uuid = UUID.randomUUID().toString();
        final MonthlyAccountBalanceIntegrationRequest monthlyAccountBalanceIntegrationRequest =
                getMonthlyAccountBalanceIntegrationRequest(monthlyAccountBalanceRequest, uuid);
        log.info("Start Monthly Account Balance ********** request = {}", monthlyAccountBalanceIntegrationRequest);
        final MonthlyAccountBalanceResponse response;
        try {
            response = monthlyAccountBalanceApiClient.monthlyAccountBalance(monthlyAccountBalanceIntegrationRequest);
            log.info("Response for Monthly Account Balance {}", response);
        } catch (Exception e) {
            log.error("Error getting the response from ESB for Monthly Account Balance {}", e.toString());
            throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
        }
        if (response.getData().getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
            log.error("Monthly Account Balance error response- {}", response);
            throw new AuBusinessException(AS0809.getHttpStatus(),
                    AS0809.getCode(),
                    response.getData().getTransactionStatus().getExtendedErrorDetails().getMessages().get(0)
                   .getMessage());
        } else {
            final MonthlyAccountBalanceStaging staging = new MonthlyAccountBalanceStaging();
            staging.setMonthlyAccountBalanceEsbRequest(getJsonString(monthlyAccountBalanceIntegrationRequest));
            staging.setAccountNo(monthlyAccountBalanceRequest.getAccountId());
            staging.setMonthlyAccountBalanceResponse(getJsonString(response));
            monthlyAccountBalanceStagingRepository.save(staging);
            log.info("Monthly Account Balance response added successfully for Monthly Account ID = {}",
                    monthlyAccountBalanceIntegrationRequest.getAccountId());

            final List<BalanceDetail> filterBalanceDetail = response.getData().getBalanceDetails().stream()
                    .filter(e -> (e.getAverageAmountType().equalsIgnoreCase(MAB_MONTHLY)))
                    .sorted(Comparator.comparing(BalanceDetail::getLogDate).reversed()).toList();


            if (filterBalanceDetail.isEmpty()) {
                log.info("No MAB Found for CASA accounts = {}", monthlyAccountBalanceIntegrationRequest.getAccountId());
                throw new AuBusinessException(AS0404.getHttpStatus(), AS0404.getCode(),
                        AS0404.getMessage());
            } else {

                List<BalanceDetailDto> result = new ArrayList<>();

                for (BalanceDetail updatedBalanceDatails : filterBalanceDetail) {
                    BalanceDetailDto balanceDetailDto = new BalanceDetailDto();
                    balanceDetailDto.setAverageAmountType(updatedBalanceDatails.getAverageAmountType());
                    balanceDetailDto.setAvgBalance(updatedBalanceDatails.getAvgBalance());
                    balanceDetailDto.setLogMonth(JsonUtil.dateToMonthName(updatedBalanceDatails.getLogDate()));
                    result.add(balanceDetailDto);
                }

                MonthlyAccountBalanceDataDto updatedResponse = new MonthlyAccountBalanceDataDto();
                updatedResponse.setUpdatedBalanceDetails(result);
                updatedResponse.setTransactionStatus(response.getData().getTransactionStatus());

                return updatedResponse;
            }
        }
    }


    private MonthlyAccountBalanceIntegrationRequest getMonthlyAccountBalanceIntegrationRequest(
            MonthlyAccountBalanceRequest monthlyAccountBalanceRequest, String uuid) {
        return MonthlyAccountBalanceIntegrationRequest.builder().accountId(monthlyAccountBalanceRequest.getAccountId())
                       .referenceNumber(uuid).requestId(uuid).originatingChannel(CHANNEL).transactionBranch(
                        monthlyAccountBalanceRequest.getTransactionBranch()).build();
    }



}
