package com.au.app.account.service.impl;

import com.au.app.account.client.MiniStatementApiClient;
import com.au.app.account.domain.entity.MiniStatementStaging;
import com.au.app.account.domain.entity.PeriodicStatementStaging;
import com.au.app.account.domain.request.mini_statement.MiniStatementIntegrationRequest;
import com.au.app.account.domain.request.mini_statement.MiniStatementRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementIntegrationRequest;
import com.au.app.account.domain.request.periodic_statement.PeriodicStatementRequest;
import com.au.app.account.domain.response.mini_statement.MiniStatementResponse;
import com.au.app.account.domain.response.mini_statement.MiniStatementResponseData;
import com.au.app.account.domain.response.periodic_statement.MiniStatementDetails;
import com.au.app.account.domain.response.periodic_statement.PeriodicStatementResponse;
import com.au.app.account.domain.response.periodic_statement.PeriodicStatementResponseData;
import com.au.app.account.repository.MiniStatementStagingRepository;
import com.au.app.account.repository.PeriodicStatementStagingRepository;
import com.au.app.account.service.MiniStatementService;
import com.au.app.account.util.JsonUtil;
import com.au.app.exception.AuBusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.UUID;

import static com.au.app.account.constant.AccountServiceConstants.*;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.*;
import static com.au.app.account.util.JsonUtil.getJsonString;

@Service
@Slf4j
@RequiredArgsConstructor
public class MiniStatementServiceImpl implements MiniStatementService {
    private final MiniStatementApiClient miniStatementApiClient;
    private final MiniStatementStagingRepository miniStatementStagingRepository;
    private final PeriodicStatementStagingRepository periodicStatementStagingRepository;

    @Override
    public MiniStatementResponseData getMiniStatementESB(MiniStatementRequest miniStatementRequest) {

        final String uuid = UUID.randomUUID().toString();
        final MiniStatementIntegrationRequest miniStatementIntegrationRequest = getMiniStatementIntegrationRequest(
                miniStatementRequest, uuid);
        log.info("Start Mini Statement ********** request = {}", miniStatementIntegrationRequest);

        final MiniStatementResponse response;

        try {
            response = miniStatementApiClient.getResponseFromMiniStatementESB(miniStatementIntegrationRequest);
            log.info("Response for Mini Statement {}", response);

        } catch (Exception e) {
            log.error("Error getting the response from ESB for Mini Statement {}", e.toString());
            throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
        }

        if (response.getData().getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
            log.error("Mini Statement error response- {}", response);
            throw new AuBusinessException(AS0809.getHttpStatus(), AS0809.getCode(),
                    response.getData().getTransactionStatus().getExtendedErrorDetails().getMessages().get(0)
                            .getMessage());
        } else {
            final MiniStatementStaging staging = new MiniStatementStaging();
            staging.setMiniStatementEsbRequest(getJsonString(miniStatementIntegrationRequest));
            staging.setAccountID(miniStatementRequest.getAccountID());
            staging.setMiniStatementResponse(getJsonString(response.getData()));
            miniStatementStagingRepository.save(staging);
            log.info("Mini Statement response added successfully for Monthly Account ID = {}",
                    miniStatementIntegrationRequest.getAccountID());
            return response.getData();
        }
    }


    @Override
    public PeriodicStatementResponseData getPeriodicStatementESB(PeriodicStatementRequest periodicStatementRequest) {

        final String uuid = UUID.randomUUID().toString();
        final PeriodicStatementIntegrationRequest periodicStatementIntegrationRequest =
                getPeriodicStatementIntegrationRequest(periodicStatementRequest, uuid);
        log.info("Start Periodic Statement ********** request = {}",
                JsonUtil.getJsonString(periodicStatementIntegrationRequest));

        final PeriodicStatementResponse response;

        try {
            response = miniStatementApiClient.getResponseFromPeriodicStatementESB(periodicStatementIntegrationRequest);
            log.info("Response for Periodic Statement {}", response);

        } catch (Exception e) {
            log.error("Error getting the response from ESB for Periodic Statement {}", e.toString());
            throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
        }

        if (response.getData().getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
            log.error("Periodic Statement error response- {}", response);
            throw new AuBusinessException(AS0809.getHttpStatus(), AS0809.getCode(),
                    response.getData().getTransactionStatus().getExtendedErrorDetails().getMessages().get(0)
                   .getMessage());
        } else {

            if (response.getData().getMiniStatementDetails().size() >= 999) {
                if (response.getData().getMiniStatementDetails().get(0).getValueDate().equals(
                        response.getData().getMiniStatementDetails().get(998).getValueDate())) {

                    final PeriodicStatementStaging staging = new PeriodicStatementStaging();
                    staging.setPeriodicStatementEsbRequest(getJsonString(periodicStatementIntegrationRequest));
                    staging.setAccountID(periodicStatementRequest.getAccountNumber());
                    staging.setPeriodicStatementResponse(getJsonString(response.getData()));
                    staging.setTransactionCount(String.valueOf(response.getData().getMiniStatementDetails().size()));
                    staging.setTransactionDate(
                            changeDateFormat(response.getData().getMiniStatementDetails().get(0).getTransactionDate()));
                    periodicStatementStagingRepository.save(staging);
                    log.info("Periodic Statement response added successfully for Monthly Account ID = {}",
                            periodicStatementRequest.getAccountNumber());


                    throw new AuBusinessException(AS0810.getHttpStatus(), AS0810.getCode(),
                            "You have more than 999 transaction on " + changeDateFormat(
                                    response.getData().getMiniStatementDetails().get(0).getTransactionDate())
                            + ". Please visit nearest branch");
                } else {
                    periodicStatementRequest.setStartDate(changeDateFormat(
                            response.getData().getMiniStatementDetails().get(998).getTransactionDate()));
                    log.info("searching for transactions again with request---------- {}", periodicStatementRequest);
                    PeriodicStatementResponseData tempResponse = getPeriodicStatementESB(periodicStatementRequest);

                    response.getData().getMiniStatementDetails().addAll(tempResponse.getMiniStatementDetails());
                    response.getData().setMiniStatementDetails(
                            response.getData().getMiniStatementDetails().stream().distinct()
                                    .sorted(Comparator.comparing(MiniStatementDetails::getTransactionDate).reversed())
                                    .toList());
                    return response.getData();

                }

            } else {

                response.getData().setMiniStatementDetails(
                        response.getData().getMiniStatementDetails().stream().distinct()
                                .sorted(Comparator.comparing(MiniStatementDetails::getTransactionDate).reversed())
                                .toList());

                final PeriodicStatementStaging staging = new PeriodicStatementStaging();
                staging.setPeriodicStatementEsbRequest(getJsonString(periodicStatementIntegrationRequest));
                staging.setAccountID(periodicStatementRequest.getAccountNumber());
                staging.setPeriodicStatementResponse(getJsonString(response.getData()));
                staging.setTransactionCount(String.valueOf(response.getData().getMiniStatementDetails().size()));
                staging.setTransactionDate(NOT_REQUIRED);
                periodicStatementStagingRepository.save(staging);
                log.info("Periodic Statement response added successfully for Monthly Account ID = {}",
                        periodicStatementRequest.getAccountNumber());


                return response.getData();
            }

        }
    }


    private MiniStatementIntegrationRequest getMiniStatementIntegrationRequest(
            MiniStatementRequest miniStatementRequest, String uuid) {
        return MiniStatementIntegrationRequest.builder().accountID(miniStatementRequest.getAccountID()).referenceNumber(
                        uuid).requestId(uuid).channel(CHANNEL).transactionBranch(miniStatementRequest
                       .getTransactionBranch())
                       .pageSize(NOT_REQUIRED).pageNumber(NOT_REQUIRED).fromDate(NOT_REQUIRED).toDate(NOT_REQUIRED)
                       .noOftransactions(miniStatementRequest.getNoOftransactions()).transactionDate(NOT_REQUIRED)
                       .build();
    }

    private PeriodicStatementIntegrationRequest getPeriodicStatementIntegrationRequest(
            PeriodicStatementRequest periodicStatementRequest, String uuid) {
        return PeriodicStatementIntegrationRequest.builder().startDate(periodicStatementRequest.getStartDate())
                       .requestId(uuid).originatingChannel(CHANNEL).referenceNumber(uuid).transactionBranch(
                        periodicStatementRequest.getTransactionBranch()).endDate(periodicStatementRequest.getEndDate())
                       .accountNumber(periodicStatementRequest.getAccountNumber()).build();
    }

    public static String changeDateFormat(String givenDate) {
        SimpleDateFormat currentFormatter = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
        SimpleDateFormat convertedOutputFormatter = new SimpleDateFormat("dd-MMM-yyyy");
        Date parsedDate;
        try {
            parsedDate = currentFormatter.parse(givenDate);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return convertedOutputFormatter.format(parsedDate);
    }
}
