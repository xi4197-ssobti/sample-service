package com.au.app.account.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "app.module-codes")
public class ModuleCodeProps {

    private HashSet<String> moduleCodeFilter;
}
