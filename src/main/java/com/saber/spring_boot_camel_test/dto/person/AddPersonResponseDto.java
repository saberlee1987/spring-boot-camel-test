package com.saber.spring_boot_camel_test.dto.person;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import com.saber.spring_boot_camel_test.dto.ErrorResponseDto;
import lombok.Data;

@Data
public class AddPersonResponseDto {
    private Integer code;
    private String text;
    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.BIG_DECIMAL)
                .create().toJson(this, AddPersonResponseDto.class);

    }
}
