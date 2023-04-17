package com.mree.demo.mavidev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties("app")
public class AppProperties {

    private List<String> publicUrls;
    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> notificationReceivers;

}
