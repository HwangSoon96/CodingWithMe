package com.ssafy.api.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.api.request.SourceCode;

import javax.persistence.AttributeConverter;

public class SourceCodeJsonConverter implements AttributeConverter<SourceCode, String> {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(SourceCode attribute) {
        //SourceCode 객체 -> Json 문자열로 변환
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public SourceCode convertToEntityAttribute(String dbData) {
        //Json 문자열 SourceCode 객체로 변환
        try {
            return objectMapper.readValue(dbData, SourceCode.class);
        } catch (Exception e) {
            return null;
        }
    }
}
