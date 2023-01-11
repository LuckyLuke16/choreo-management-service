package com.example.managementservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONHelper {

    public static String toJson(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
