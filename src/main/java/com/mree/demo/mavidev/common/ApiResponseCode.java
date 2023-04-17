package com.mree.demo.mavidev.common;

import lombok.Getter;

@Getter
public enum ApiResponseCode {

    // Generic Response
    SUCCESS(1000, "Success", true),
    SYSTEM_ERROR(1001, "System error is occurred!"),
    INVALID_REQUEST(1002, "Invalid request!"),
    AUTHORIZATION_FAILED(1003, "Authorization is failed!"),
    ILLEGAL_SERVICE_STATE(1004, "Illegal service State!"),
    WRONG_INPUT_EXCEPTION(1005, "Wrong input!"),
    VALIDATION_ERROR(1006, "validation error!"),
    CITY_NOT_FOUND(1007, "City not found!"),
    COUNTRY_NOT_FOUND(1008, "Country not found!"),
    CITY_ALREADY_EXIST(1009, "City already exist!"),
    COUNTRY_ALREADY_EXIST(1010, "Country already exist!");

    private final int code;
    private final String message;
    private final boolean success;

    ApiResponseCode(int code, String message) {
        this(code, message, false);
    }

    ApiResponseCode(int code, String message, boolean success) {
        this.code = code;
        this.message = message;
        this.success = success;
    }
}
