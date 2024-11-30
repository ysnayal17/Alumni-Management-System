package com.alumnimanagement.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.servlet.ServletInputStream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

public class ObjectMapperUtil {

    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        mapper.registerModule(new JavaTimeModule());
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    private ObjectMapperUtil() {
    }

    public static <T> T toObject(String jsonObject, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonObject, clazz);
    }

    public static <T> T toObject(String jsonObject, Class<?> toClazz, Class<?> containedClazz) throws IOException {
        JavaType type = mapper.getTypeFactory().constructParametricType(toClazz, containedClazz);
        return mapper.readValue(jsonObject, type);
    }

    public static <T> T toObject(ServletInputStream inputStream, Class<T> clazz) throws IOException {
        return mapper.readValue(getStringFromInputStream(inputStream), clazz);
    }

    public static <T> T toObject(String jsonObject, TypeReference<T> typeRef) throws IOException {
        return mapper.readValue(jsonObject, typeRef);
    }

    public static String getStringFromInputStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        String line;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
        } catch (IOException e) {
            //supressing exception
        }
        return sb.toString();
    }

    public static String objectToString(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    public static <T, V> T toObject(Map<String, V> requestParams, Class<T> clazz) {
        return mapper.convertValue(requestParams, clazz);
    }

    public static <T> T toGenericObject(Map<String, Object> reqParam, Class<T> clazz) {
        return mapper.convertValue(reqParam, clazz);
    }

    public static String toString(Object classObject) throws JsonProcessingException {
        return mapper.writeValueAsString(classObject);
    }

    public static Map<String, Object> toMap(String jsonInStringType) throws IOException {
        return mapper.readValue(jsonInStringType, new TypeReference<HashMap<String, Object>>() {

        });
    }

    public static <V> Map<String, V> toMapDynamic(String jsonInStringType) throws IOException {
        return mapper.readValue(jsonInStringType, new TypeReference<HashMap<String, V>>() {

        });
    }

    public static <T> T toGenericMap(String jsonInStringType, T type) throws IOException {
        return mapper.readValue(jsonInStringType, new TypeReference<T>() {

        });
    }

    public static Map<String, Long> toGenericLongMap(String jsonInStringType) throws IOException {
        return mapper.readValue(jsonInStringType, new TypeReference<Map<String, Long>>() {

        });
    }
}

