package com.saber.spring_boot_camel_test.services;


import com.saber.spring_boot_camel_test.dto.hi.HelloDto;
import com.saber.spring_boot_camel_test.dto.hi.HelloRequestDto;

public interface HelloService {
    public HelloDto sayHello(String firstName,String lastName);
    public HelloDto sayHello(HelloRequestDto dto);
}
