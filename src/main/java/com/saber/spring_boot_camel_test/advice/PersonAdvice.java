package com.saber.spring_boot_camel_test.advice;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.saber.spring_boot_camel_test.dto.ErrorResponseDto;
import com.saber.spring_boot_camel_test.dto.ServiceResponseErrorEnum;
import com.saber.spring_boot_camel_test.dto.ValidationDto;
import com.saber.spring_boot_camel_test.exceptions.ResourceDuplicationException;
import com.saber.spring_boot_camel_test.exceptions.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
@Slf4j
public class PersonAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceNotFoundException(ResourceNotFoundException ex){

        ErrorResponseDto error = new ErrorResponseDto();
        error.setCode(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getCode());
        error.setText(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getText());
        error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                ,HttpStatus.NOT_ACCEPTABLE.value(),ex.getMessage()));
        log.error("Error for ResourceNotFoundException with body {}",error);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(JsonMappingException.class)
    public ResponseEntity<ErrorResponseDto> handleJsonMappingException(JsonMappingException ex){

        ErrorResponseDto error = new ErrorResponseDto();
        error.setCode(ServiceResponseErrorEnum.JSON_MAPPING_ERROR.getCode());
        error.setText(ServiceResponseErrorEnum.JSON_MAPPING_ERROR.getText());
        error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                ,HttpStatus.NOT_ACCEPTABLE.value(),ex.getMessage()));
        log.error("Error for handleJsonMappingException with body {}",error);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(JsonParseException.class)
    public ResponseEntity<ErrorResponseDto> handleJsonParseException(JsonParseException ex){

        ErrorResponseDto error = new ErrorResponseDto();
        error.setCode(ServiceResponseErrorEnum.JSON_PARSER_ERROR.getCode());
        error.setText(ServiceResponseErrorEnum.JSON_PARSER_ERROR.getText());
        error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                ,HttpStatus.NOT_ACCEPTABLE.value(),ex.getMessage()));
        log.error("Error for handleJsonParseException with body {}",error);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(ResourceDuplicationException.class)
    public ResponseEntity<ErrorResponseDto> handleResourceDuplicationException(ResourceDuplicationException ex){

        ErrorResponseDto error = new ErrorResponseDto();
        error.setCode(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getCode());
        error.setText(ServiceResponseErrorEnum.PERSON_SERVICE_PROVIDER_ERROR.getText());
        error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                ,HttpStatus.NOT_ACCEPTABLE.value(),ex.getMessage()));
        log.error("Error for ResourceDuplicationException with body {}",error);
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(error);
    }

    @ExceptionHandler(value = ConstraintViolationException.class)
    public ResponseEntity<ErrorResponseDto> handleParameterNotValid(ConstraintViolationException ex){
        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setCode(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getCode());
        errorResponse.setText(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getText());
        List<ValidationDto> validationDtoList = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setFieldName(violation.getPropertyPath().toString());
            validationDto.setConstraintMessage(violation.getMessage());
            validationDtoList.add(validationDto);
        }
        errorResponse.setValidations(validationDtoList);
        log.error("Error for ResourceDuplicationException with body {}",errorResponse);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException exception, HttpHeaders headers, HttpStatus status, WebRequest request) {

        ErrorResponseDto errorResponse = new ErrorResponseDto();
        errorResponse.setCode(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getCode());
        errorResponse.setText(ServiceResponseErrorEnum.INPUT_VALIDATION_ERROR.getText());
        List<ValidationDto> validationDtoList = new ArrayList<>();
        for (FieldError fieldError : exception.getFieldErrors()) {
            ValidationDto validationDto = new ValidationDto();
            validationDto.setFieldName(fieldError.getField());
            validationDto.setConstraintMessage(fieldError.getDefaultMessage());
            validationDtoList.add(validationDto);
        }
        errorResponse.setValidations(validationDtoList);

        log.error("Error for  handleMethodArgumentNotValid with body ===> {}", errorResponse);
        return ResponseEntity.status(status).body(errorResponse);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponseDto> handleJsonRuntimeException(RuntimeException ex){

        ErrorResponseDto error = new ErrorResponseDto();
        error.setCode(ServiceResponseErrorEnum.INTERNAL_SERVICE_ERROR.getCode());
        error.setText(ServiceResponseErrorEnum.INTERNAL_SERVICE_ERROR.getText());
        error.setOriginalMessage(String.format("{\"code\":%d,\"text\":\"%s\"}"
                ,HttpStatus.INTERNAL_SERVER_ERROR.value(),ex.getMessage()));
        log.error("Error for handleJsonRuntimeException with body {}",error);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

}
