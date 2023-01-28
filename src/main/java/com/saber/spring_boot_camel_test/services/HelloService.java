package com.saber.spring_boot_camel_test.services;


import com.saber.spring_boot_camel_test.dto.hi.HelloDto;
import com.saber.spring_boot_camel_test.dto.hi.HelloRequestDto;
import org.apache.camel.Body;
import org.apache.camel.Header;

public interface HelloService {

    public HelloDto sayHelloGet(@Header(value = "firstName") String firstName,@Header(value = "lastName") String lastName);
    public HelloDto sayHelloPost(@Body HelloRequestDto dto);
}
