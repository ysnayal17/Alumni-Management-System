package com.alumnimanagement.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Map;

public class ObjectMapperUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private ObjectMapperUtil() {
    }

    public static <T, V> T toObject(Map<String, V> requestParams, Class<T> clazz) {
        return mapper.convertValue(requestParams, clazz);
    }
}

