package com.mree.demo.mavidev.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    private boolean success;
    private String message;
    private Integer code;
    private T data;

    public static <T> ApiResponse<T> empty() {
        return success(null);
    }

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .code(ApiResponseCode.SUCCESS.getCode())
                .message(ApiResponseCode.SUCCESS.getMessage())
                .data(data)
                .success(true)
                .build();
    }

    public static <T> ApiResponse<T> error() {
        return ApiResponse.<T>builder()
                .code(ApiResponseCode.SYSTEM_ERROR.getCode())
                .message(ApiResponseCode.SYSTEM_ERROR.name())
                .success(false)
                .build();
    }
}
