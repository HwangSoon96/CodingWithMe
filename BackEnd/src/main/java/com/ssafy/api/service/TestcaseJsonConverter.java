package com.ssafy.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.request.TestCase;

import javax.persistence.AttributeConverter;

public class TestcaseJsonConverter  implements AttributeConverter<TestCase, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(TestCase attribute) {
        //Information 객체 -> Json 문자열로 변환
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public TestCase convertToEntityAttribute(String dbData) {
        //Json 문자열 Information 객체로 변환
        try {
            return objectMapper.readValue(dbData, TestCase.class);
        } catch (Exception e) {
            return null;
        }
    }
}
