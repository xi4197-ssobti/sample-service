package com.au.app.account.service.impl;

import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsIntegrationRequest;
import com.au.app.account.domain.request.debit_card_details.DebitCardDetailsRequest;
import com.au.app.account.domain.response.debit_card_details.CardDetail;
import com.au.app.account.domain.response.debit_card_details.DebitCardResponseData;
import com.au.app.exception.AuBusinessException;
import com.au.app.account.client.DebitCardDetailsApiClient;
import com.au.app.account.domain.entity.DebitCardDetailsStaging;
import com.au.app.account.domain.response.debit_card_details.DebitCardDetailsResponse;
import com.au.app.account.repository.DebitCardDetailsRepository;
import com.au.app.account.service.DebitCardDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.UUID;

import static com.au.app.account.constant.AccountServiceConstants.*;
import static com.au.app.account.domain.enums.ErrorExceptionCodes.*;
import static com.au.app.account.util.JsonUtil.getJsonString;


@Service
@RequiredArgsConstructor
@Slf4j
public class DebitCardDetailsServiceImpl implements DebitCardDetailsService {
    private final DebitCardDetailsApiClient debitCardDetailsApiClient;
    private final DebitCardDetailsRepository debitCardDetailsRepository;

    @Override
    public DebitCardResponseData getDebitCardDetails(DebitCardDetailsRequest debitCardDetailsRequest) {

        final String uuid = UUID.randomUUID().toString();
        final DebitCardDetailsIntegrationRequest request = getDebitCardDetailsIntegrationRequest(
                debitCardDetailsRequest, uuid);

        log.info("Start Debit Card Details ********** request = {}", request);
        final DebitCardDetailsResponse response;
        try {
            response = debitCardDetailsApiClient.getDebitCardDetailsResponse(request);
            log.info("Response for Debit Card Details {}", response);
        } catch (Exception e) {
            log.error("Error getting the response from ESB for Debit Card Details {}", e.toString());
            throw new AuBusinessException(AS0500.getHttpStatus(), AS0500.getCode(), AS0500.getMessage());
        }

        if (response.getData().getTransactionStatus().getResponseCode().equals(ERROR_CODE)) {
            log.error("Debit Card Details error response- {}", response);
            throw new AuBusinessException(AS0809.getHttpStatus(), AS0809.getCode(),
                    response.getData().getTransactionStatus().getExtendedErrorDetails().getMessages().get(0)
                            .getMessage());
        } else {

            final List<CardDetail> filterCards = response.getData().getCardDetails().stream().filter(
                    e -> (e.getPrimaryAcctNumber().equalsIgnoreCase(debitCardDetailsRequest.getAccountNo()))
                         && (e.getActiveInactive().equalsIgnoreCase(DEBIT_CARD_STATUS))).sorted(
                    Comparator.comparing(CardDetail::getExpiryDate).reversed()).limit(1).toList();
            if (filterCards.isEmpty()) {
                log.info("No Debit Card found for CASA accounts = {}", debitCardDetailsRequest.getAccountNo());
                throw new AuBusinessException(AS0404.getHttpStatus(), AS0404.getCode(), AS0404.getMessage());
            } else {

                response.getData().setCardDetails(filterCards);

                final DebitCardDetailsStaging staging = new DebitCardDetailsStaging();
                staging.setDebitCardDetailsEsbRequest(getJsonString(request));
                staging.setCustomerId(request.getCustomerId());
                staging.setDebitCardDetailsEsbResponse(getJsonString(response.getData()));
                debitCardDetailsRepository.save(staging);
                log.info("Debit Card Details response added successfully for Customer ID = {}",
                        request.getCustomerId());
                return response.getData();
            }
        }


    }

    private DebitCardDetailsIntegrationRequest getDebitCardDetailsIntegrationRequest(
            DebitCardDetailsRequest debitCardDetailsRequest, String uuid) {
        return DebitCardDetailsIntegrationRequest.builder().customerId(debitCardDetailsRequest.getCustomerId())
                       .requestId(uuid).channel(CHANNEL).build();
    }
}
