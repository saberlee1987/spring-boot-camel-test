package com.saber.spring_boot_camel_test.dto;

public enum ServiceResponseErrorEnum {

    JSON_MAPPING_ERROR(1, "خطای پردازش json mapping"),
    JSON_PARSER_ERROR(1, "خطای پردازش json parser"),
    PERSON_SERVICE_PROVIDER_ERROR(2, "خطای سرویس دهنده"),
    INPUT_VALIDATION_ERROR(3, "خطای اعتبار سنجی داده ورودی"),
    INTERNAL_SERVICE_ERROR(4, "خطای داخلی سامانه"),
    TIMEOUT_ERROR(5, "خطای عدم برقراری ارتباط"),
    ;
    private final Integer code;
    private final String text;

    ServiceResponseErrorEnum(Integer code, String text) {
        this.code = code;
        this.text = text;
    }

    public Integer getCode() {
        return code;
    }

    public String getText() {
        return text;
    }
}
