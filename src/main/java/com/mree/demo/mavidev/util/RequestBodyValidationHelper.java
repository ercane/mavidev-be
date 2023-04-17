package com.mree.demo.mavidev.util;


import com.mree.demo.mavidev.exception.RequestBodyValidationException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class RequestBodyValidationHelper {

    private static final String MESSAGE_SEPERATOR = " | ";
    private final Validator validator;

    public <T> void validate(T t) {
        Set<ConstraintViolation<T>> violetList = validator.validate(t);
        StringBuilder sb = new StringBuilder();


        NullHelper.nullSafeSet(violetList).forEach(v -> {
            String violationMessage = v.getMessage();
            if (StringUtils.hasLength(violationMessage)) {
                sb.append(violationMessage);
                sb.append(MESSAGE_SEPERATOR);
            }
        });

        String message = sb.toString();
        if (!violetList.isEmpty() && StringUtils.hasLength(message)) {
            message = message.substring(0, message.length() - MESSAGE_SEPERATOR.length());
            throw new RequestBodyValidationException(message);
        }
    }

}
