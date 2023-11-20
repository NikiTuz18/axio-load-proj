package ru.nikituz.rest.configurations.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "soap")
@Getter
@Setter
public class SoapProperties {
    private String uri;
}
