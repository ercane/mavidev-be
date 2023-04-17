package com.mree.demo.mavidev.config;


import org.springframework.util.StringUtils;
import org.springframework.web.cors.CorsConfiguration;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexCorsConfiguration extends CorsConfiguration {


    /**
     * Check the origin of the request against the configured allowed origins.
     *
     * @param requestOrigin the origin to check
     * @return the origin to use for the response, possibly {@code null} which
     * means the request origin is not allowed
     */
    public String checkOrigin(String requestOrigin) {
        if (!StringUtils.hasText(requestOrigin)) {
            return null;
        }

        if (this.getAllowedOrigins().isEmpty()) {
            return null;
        }

        if (this.getAllowedOrigins().contains(ALL)) {
            if (getAllowCredentials() != Boolean.TRUE) {
                return ALL;
            } else {
                return requestOrigin;
            }
        }

        for (String allowedOriginRegex : this.getAllowedOrigins()) {
            if (createMatcher(requestOrigin, allowedOriginRegex).matches()) {
                return requestOrigin;
            }
        }

        return null;
    }

    private Matcher createMatcher(String origin, String allowedOrigin) {
        String regex = this.parseAllowedWildcardOriginToRegex(allowedOrigin);
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(origin);
    }

    private String parseAllowedWildcardOriginToRegex(String allowedOrigin) {
        String regex = allowedOrigin.replace(".", "\\.");
        return regex.replace("*", ".*");
    }

}
