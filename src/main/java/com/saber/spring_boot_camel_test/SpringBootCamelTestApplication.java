package com.saber.spring_boot_camel_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class SpringBootCamelTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootCamelTestApplication.class, args);
    }

}
