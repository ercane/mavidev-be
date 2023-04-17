package com.mree.demo.mavidev.exception;

import com.mree.demo.mavidev.common.ApiResponseCode;

public class RequestBodyValidationException extends BaseException {
    public RequestBodyValidationException(String message) {
        super(message, ApiResponseCode.VALIDATION_ERROR);
    }
}
