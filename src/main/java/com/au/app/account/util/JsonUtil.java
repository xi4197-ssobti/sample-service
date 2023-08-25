package com.au.app.account.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.extern.slf4j.Slf4j;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public final class JsonUtil {

    private JsonUtil() {
        // Not Called
    }

    public static String getJsonString(Object response) {
        String responseString = null;
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        try {
            responseString = objectMapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            log.error("error converting json to string" + e);
        }
        return responseString;
    }
    public static String dateToMonthName(Date givenDate) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMMM");
        return dateFormat.format(givenDate);
    }


    public static String changeDateFormat(String givenDate) {

        SimpleDateFormat  currentFormatter = new SimpleDateFormat("yyyyy-MM-dd HH:mm:ss");
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
