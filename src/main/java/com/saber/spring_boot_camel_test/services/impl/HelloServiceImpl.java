package com.saber.spring_boot_camel_test.services.impl;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import com.saber.spring_boot_camel_test.dto.hi.HelloDto;
import com.saber.spring_boot_camel_test.services.HelloService;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service(value = "helloService")
@Slf4j
public class HelloServiceImpl implements HelloService {

    @Override
    public HelloDto sayHello(@Header(value = "firstName") String firstName,@Header(value = "lastName") String lastName) {
        Map<String, String> json = new HashMap<>();
        json.put("firstName", firstName);
        json.put("lastName", lastName);
        log.info("Request for say Hello with parameter {}", json);
        String message = String.format("Hello %s %s", firstName, lastName);
        HelloDto helloDto = new HelloDto();
        helloDto.setMessage(message);
        log.info("Response for say Hello ===> {}", helloDto);
        return helloDto;
    }
}
