package com.au.app.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.current-status")
public class CurrentStatusProps {

    private HashSet<String> depositSummary;
}
