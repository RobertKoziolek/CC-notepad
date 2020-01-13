package com.robcio.ccnotepad.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:links.properties")
@ConfigurationProperties
@Configuration
@Getter
@Setter
public class LinkConfiguration {

    private String futureLink;
    private String dateLink;


}
