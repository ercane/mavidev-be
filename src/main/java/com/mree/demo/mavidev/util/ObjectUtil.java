package com.mree.demo.mavidev.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class ObjectUtil {

    public static ObjectMapper jsonObjectMapper() {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        // add joda BigMoney support
        SimpleModule simpleModule = new SimpleModule();
        mapper.registerModule(simpleModule);

        mapper.registerModule(simpleModule);

        // add serialization feature WRITE_ENUMS_USING_TO_STRING
        mapper.enable(SerializationFeature.WRITE_ENUMS_USING_TO_STRING);

        // add deserialization feature FAIL_ON_UNKNOWN_PROPERTIES
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        // disable serialize failing on empty beans
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        // enable to seen as '2019-01-20T14:20:36.15'
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        return mapper;
    }


    public static String prettyPrint(Object value, ObjectMapper objectMapper) {

        if (value != null) {

            if (objectMapper == null)
                objectMapper = jsonObjectMapper();

            try {
                return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(value);

            } catch (JsonProcessingException e) {
                log.error("Object pretty print has failed! Value: {}", value, e);
            }
        }

        return null;
    }
}
