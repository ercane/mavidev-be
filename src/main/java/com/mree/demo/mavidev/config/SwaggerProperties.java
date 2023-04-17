package com.mree.demo.mavidev.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Set;

@Data
@Component
@ConfigurationProperties(value = "app.swagger")
public class SwaggerProperties {
    private String name;
    private String desc;
    private String version;
    private Set<String> urls;
}
