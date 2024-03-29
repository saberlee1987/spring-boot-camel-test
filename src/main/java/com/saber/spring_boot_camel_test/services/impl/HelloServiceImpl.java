package com.saber.spring_boot_camel_test.services.impl;

import com.saber.spring_boot_camel_test.annotations.SayHelloAnnotation;
import com.saber.spring_boot_camel_test.dto.hi.HelloDto;
import com.saber.spring_boot_camel_test.dto.hi.HelloRequestDto;
import com.saber.spring_boot_camel_test.services.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Handler;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

@Service(value = "helloService")
@Slf4j
public class HelloServiceImpl implements HelloService {
        /// 45000000000000000000
    @Override
    @Handler
    public HelloDto sayHelloGet(@Header(value = "firstName") String firstName,@Header(value = "lastName") String lastName) {
        Map<String, String> json = new HashMap<>();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        log.info("Request for say Hello with parameter {}", json);
        String message = String.format("Hello %s %s", firstName, lastName);
        HelloDto helloDto = new HelloDto();
        helloDto.setMessage(message);
        helloDto.setNumber(new BigInteger("9999999999999999999"));
        helloDto.setNumber2(new BigDecimal("9999999999999999999"));
        log.info("Response for say Hello ===> {}", helloDto);
        return helloDto;
    }

    @Override
    @SayHelloAnnotation
    public HelloDto sayHelloPost(@Body HelloRequestDto dto) {
        String firstName = dto.getFirstName();
        String lastName = dto.getLastName();
        log.info("Request for say Hello with body {}", dto);
        String message = String.format("Hello %s %s", firstName, lastName);
        HelloDto helloDto = new HelloDto();
        helloDto.setMessage(message);
        log.info("Response for say Hello ===> {}", helloDto);
        return helloDto;
    }
}
