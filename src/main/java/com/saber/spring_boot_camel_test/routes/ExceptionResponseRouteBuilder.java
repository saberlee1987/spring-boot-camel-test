package com.saber.spring_boot_camel_test.routes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.saber.spring_boot_camel_test.dto.ErrorResponseDto;
import com.saber.spring_boot_camel_test.dto.ServiceResponseErrorEnum;
import com.saber.spring_boot_camel_test.dto.ValidationDto;
import com.saber.spring_boot_camel_test.exceptions.ResourceDuplicationException;
import com.saber.spring_boot_camel_test.exceptions.ResourceNotFoundException;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.support.processor.PredicateValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExceptionResponseRouteBuilder extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        from(String.format("direct:%s", Routes.RESOURCE_NOTFOUND_EXCEPTION_ROUTE))
                .routeId(Routes.RESOURCE_NOTFOUND_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.NOT_ACCEPTABLE.value()))
                .process(exchange -> {
                    ResourceNotFoundException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ResourceNotFoundException.class);
                    ErrorResponseDto error = new ErrorResponseDto();
                    error.setCode(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getCode());
                    error.setText(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getText());
                    error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                            , HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()));
                    log.error("Error for ResourceNotFoundException with body {}", error);
                    exchange.getIn().setBody(error);
                });

        from(String.format("direct:%s", Routes.RESOURCE_DUPLICATION_EXCEPTION_ROUTE))
                .routeId(Routes.RESOURCE_DUPLICATION_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.NOT_ACCEPTABLE.value()))
                .process(exchange -> {
                    ResourceDuplicationException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, ResourceDuplicationException.class);
                    ErrorResponseDto error = new ErrorResponseDto();
                    error.setCode(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getCode());
                    error.setText(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getText());
                    error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                            , HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()));
                    log.error("Error for ResourceDuplicationException with body {}", error);
                    exchange.getIn().setBody(error);
                });


        from(String.format("direct:%s", Routes.JSON_MAPPING_EXCEPTION_ROUTE))
                .routeId(Routes.JSON_MAPPING_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.NOT_ACCEPTABLE.value()))
                .process(exchange -> {
                    JsonMappingException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, JsonMappingException.class);
                    ErrorResponseDto error = new ErrorResponseDto();
                    error.setCode(ServiceResponseErrorEnum.JSON_MAPPING_ERROR.getCode());
                    error.setText(ServiceResponseErrorEnum.JSON_MAPPING_ERROR.getText());
                    error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                            , HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()));
                    log.error("Error for JsonMappingException with body {}", error);
                    exchange.getIn().setBody(error);
                });

        from(String.format("direct:%s", Routes.JSON_PARSER_EXCEPTION_ROUTE))
                .routeId(Routes.JSON_PARSER_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.NOT_ACCEPTABLE.value()))
                .process(exchange -> {
                    JsonParseException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, JsonParseException.class);
                    ErrorResponseDto error = new ErrorResponseDto();
                    error.setCode(ServiceResponseErrorEnum.JSON_PARSER_ERROR.getCode());
                    error.setText(ServiceResponseErrorEnum.JSON_PARSER_ERROR.getText());
                    error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                            , HttpStatus.NOT_ACCEPTABLE.value(), ex.getMessage()));
                    log.error("Error for JsonParseException with body {}", error);
                    exchange.getIn().setBody(error);
                });

        from(String.format("direct:%s", Routes.BEAN_VALIDATION_EXCEPTION_ROUTE))
                .routeId(Routes.BEAN_VALIDATION_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
                .process(exchange -> {
                    BeanValidationException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, BeanValidationException.class);
                    ErrorResponseDto error = new ErrorResponseDto();
                    error.setCode(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getCode());
                    error.setText(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getText());
                    List<ValidationDto> validationDtoList = new ArrayList<>();
                    for (ConstraintViolation<Object> violation : ex.getConstraintViolations()) {
                        ValidationDto validationDto = new ValidationDto();
                        validationDto.setFieldName(violation.getPropertyPath().toString());
                        validationDto.setConstraintMessage(violation.getMessage());
                        validationDtoList.add(validationDto);
                    }
                    error.setValidations(validationDtoList);
                    log.error("Error for BeanValidationException with body {}", error);
                    exchange.getIn().setBody(error);
                });

        from(String.format("direct:%s", Routes.PREDICATE_VALIDATION_EXCEPTION_ROUTE))
                .routeId(Routes.PREDICATE_VALIDATION_EXCEPTION_ROUTE)
                .routeGroup(Routes.EXCEPTION_HANDLER_ROUTE_GROUP)
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(HttpStatus.BAD_REQUEST.value()))
                .process(exchange -> {
                    PredicateValidationException ex = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, PredicateValidationException.class);
                    ErrorResponseDto error = new ErrorResponseDto();
                    error.setCode(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getCode());
                    error.setText(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getText());
                    List<ValidationDto> validationDtoList = new ArrayList<>();

                    ValidationDto validationDto = new ValidationDto();
                    validationDto.setFieldName(ex.getPredicate().toString());
                    validationDto.setConstraintMessage(ex.getMessage());
                    validationDtoList.add(validationDto);

                    error.setValidations(validationDtoList);
                    log.error("Error for PredicateValidationException with body {}", error);
                    exchange.getIn().setBody(error);
                });

    }
}
