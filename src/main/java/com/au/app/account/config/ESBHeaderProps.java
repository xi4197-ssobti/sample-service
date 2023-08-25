package com.au.app.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.feign.esb")
public class ESBHeaderProps {

    private Map<String, String> headerMap;

}
