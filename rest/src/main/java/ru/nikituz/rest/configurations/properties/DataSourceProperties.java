package ru.nikituz.rest.configurations.properties;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Getter
public class DataSourceProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
}
