package com.saber.spring_boot_camel_test.dto.hi;

import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.ToNumberPolicy;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class HelloDto {
    private String message;
    private BigInteger number;
    private BigDecimal number2;

    @Override
    public String toString() {
        return new GsonBuilder()
                .setLenient()
                .setPrettyPrinting()
                .enableComplexMapKeySerialization()
                .setLongSerializationPolicy(LongSerializationPolicy.DEFAULT)
                .setObjectToNumberStrategy(ToNumberPolicy.BIG_DECIMAL)
                .create().toJson(this, HelloDto.class);

    }
}
