package com.duzhuo.common.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * JSON解析处理
 * 
 * @author wanhy
 */
public class JSON {
    public static final String DEFAULT_FAIL = "\"Parse failed\"";
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private static final ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();

    public static void marshal(File file, Object value) throws IOException {
        objectWriter.writeValue(file, value);
    }

    public static void marshal(OutputStream os, Object value) throws IOException {
        objectWriter.writeValue(os, value);
    }

    public static String marshal(Object value) throws JsonProcessingException {
        return objectWriter.writeValueAsString(value);
    }

    public static byte[] marshalBytes(Object value) throws JsonProcessingException {
        return objectWriter.writeValueAsBytes(value);
    }

    public static <T> T unmarshal(File file, Class<T> valueType) throws IOException {
        return objectMapper.readValue(file, valueType);
    }

    public static <T> T unmarshal(InputStream is, Class<T> valueType) throws IOException {
        return objectMapper.readValue(is, valueType);
    }

    public static <T> T unmarshal(String str, Class<T> valueType) throws JsonProcessingException {
        return objectMapper.readValue(str, valueType);
    }

    public static <T> T unmarshal(byte[] bytes, Class<T> valueType) throws IOException {
        if (bytes == null) {
            bytes = new byte[0];
        }
        return objectMapper.readValue(bytes, 0, bytes.length, valueType);
    }
}
