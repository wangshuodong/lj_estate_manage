package com.ym.common.utils;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonBuilder {

    private static JacksonBuilder instance;
    private ObjectMapper mapper;

    private JacksonBuilder() {
        mapper = new ObjectMapper();
    }

    private static JacksonBuilder getInstance() {
        synchronized (JacksonBuilder.class) {
            if (instance == null) {
                instance = new JacksonBuilder();
            }
        }
        return instance;
    }

    public static ObjectMapper mapper() {
        return getInstance().mapper;
    }
}
