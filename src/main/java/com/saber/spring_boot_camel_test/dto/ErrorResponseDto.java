package com.saber.spring_boot_camel_test.dto;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

import java.util.List;
@Data
public class ErrorResponseDto {
    private Integer code;
    private String text;
    @JsonRawValue
    private Object originalMessage;
    private List<ValidationDto> validations;
    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.BIG_DECIMAL)
                .create().toJson(this, ErrorResponseDto.class);

    }
}
