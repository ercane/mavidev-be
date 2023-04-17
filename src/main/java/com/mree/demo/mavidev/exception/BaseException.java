package com.mree.demo.mavidev.exception;

import com.mree.demo.mavidev.common.ApiResponseCode;
import lombok.Getter;
import lombok.Setter;

@Getter
public abstract class BaseException extends RuntimeException {

    private final ApiResponseCode responseCode;

    @Setter
    private Object data;

    protected BaseException(String message, Throwable cause, ApiResponseCode responseCode) {
        super(message, cause);
        this.responseCode = responseCode;
    }

    protected BaseException(String message, ApiResponseCode responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    protected BaseException(Throwable cause, ApiResponseCode responseCode) {
        super(responseCode.getMessage(), cause);
        this.responseCode = responseCode;
    }

    public BaseException(ApiResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }
}
