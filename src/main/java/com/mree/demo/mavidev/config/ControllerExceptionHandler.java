package com.mree.demo.mavidev.config;


import com.mree.demo.mavidev.common.ApiResponse;
import com.mree.demo.mavidev.common.ApiResponseCode;
import com.mree.demo.mavidev.exception.BaseException;
import com.mree.demo.mavidev.exception.RequestBodyValidationException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.nio.file.AccessDeniedException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * To handle exceptions occurred during API Calls
 */
@Slf4j
@RequiredArgsConstructor
@ControllerAdvice
public class ControllerExceptionHandler {

    private static final String HANDLED_ERROR = "Handled Error";


    /**
     * when client tries to reach resource that need authentication
     *
     * @param exception exception occurred
     * @return response for the client
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ApiResponse<Object>> handle(NoHandlerFoundException exception) {

        ApiResponse errorResponse = ApiResponse.builder()
                .code(ApiResponseCode.SYSTEM_ERROR.getCode())
                .message(String.format("%s %s handler not found", exception.getHttpMethod(), exception.getRequestURL()))
                .build();
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    /**
     * when client makes not a formal request
     *
     * @param request   client request
     * @param exception exception occurred
     * @return response for the client
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(HttpServletRequest request
            , MethodArgumentNotValidException exception) {
        log.info(HANDLED_ERROR, exception);

        List<ObjectError> errors = exception.getBindingResult().getAllErrors().stream()
                .filter(i -> i.getDefaultMessage() != null).collect(Collectors.toList());

        List<String> errorMessages = new ArrayList<>();
        if (!CollectionUtils.isEmpty(errors)) {
            errorMessages.addAll(
                    errors
                            .stream()
                            .map(ObjectError::getDefaultMessage)
                            .filter(Objects::nonNull)
                            .collect(Collectors.toList())
            );
        }
        var errorResponse = ApiResponse.builder()
                .code(ApiResponseCode.SYSTEM_ERROR.getCode())
                .data(errorMessages)
                .build();
        return ResponseEntity
                .badRequest()
                .body(errorResponse);
    }

    /**
     * when an un-expected problem occurred on the service
     *
     * @param throwable problem cause
     * @return response for the client
     */
    @ExceptionHandler({Throwable.class})
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Throwable throwable) {
        log.info(throwable.getMessage(), throwable);
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.error());
    }

    /**
     * when some client tries to reach resource that it has no access permission
     *
     * @param exception exception occurred
     * @return response for the client
     */
    @ExceptionHandler({AccessDeniedException.class})
    public ResponseEntity<ApiResponse<Object>> handleKCUserNotFoundException(AccessDeniedException exception) {
        log.info(HANDLED_ERROR, exception);
        var errorResponse = ApiResponse.builder()
                .code(ApiResponseCode.AUTHORIZATION_FAILED.getCode())
                .message(ApiResponseCode.AUTHORIZATION_FAILED.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(errorResponse);
    }


    @ExceptionHandler({Exception.class})
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(Exception exception) {
        log.info(HANDLED_ERROR, exception);
        var responseCode = ApiResponseCode.SYSTEM_ERROR;
        var errorResponse = ApiResponse.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }

    @ExceptionHandler({BaseException.class})
    public ResponseEntity<ApiResponse<Object>> handleBaseException(BaseException exception) {
        log.info(HANDLED_ERROR, exception);
        var responseCode = exception.getResponseCode();
        ApiResponse<Object> errorResponse = ApiResponse.builder()
                .code(responseCode.getCode())
                .message(exception.getMessage())
                .data(exception.getData())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }


    @ExceptionHandler({RequestBodyValidationException.class})
    public ResponseEntity<ApiResponse<Object>> handleDtoValidationException(RequestBodyValidationException exception) {
        log.info(HANDLED_ERROR, exception);
        var errorResponse = ApiResponse.builder()
                .code(exception.getResponseCode().getCode())
                .message(exception.getMessage())
                .success(false)
                .build();
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(errorResponse);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(RuntimeException exception) {
        log.error(HANDLED_ERROR, exception);
        var responseCode = ApiResponseCode.SYSTEM_ERROR;
        var errorResponse = ApiResponse.builder()
                .code(responseCode.getCode())
                .message(responseCode.getMessage())
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(errorResponse);
    }
}
