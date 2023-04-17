package com.mree.demo.mavidev.common.ref;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum MailTemplate {
    CITY_UPDATED("City Updated"),
    CITY_DELETED("City Deleted"),
    CITY_CREATED("City Created"),
    COUNTRY_UPDATED("Country Updated"),
    COUNTRY_DELETED("Country Deleted"),
    COUNTRY_CREATED("Country Created");

    @Getter
    private String subject;
}
