package com.saber.spring_boot_camel_test.routes;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.rest.RestBindingMode;
import org.springframework.stereotype.Component;

@Component
public class RouteDefinition extends RouteBuilder {
    @Override
    public void configure() throws Exception {

        restConfiguration()
                .contextPath("{{service.api.camel.base-path}}")
                .apiContextPath("{{service.swagger.path}}")
                .bindingMode(RestBindingMode.json)
                .enableCORS(true)
                .apiProperty("api.title","{{service.swagger.camel.title}}")
                .apiProperty("api.version","{{service.swagger.version}}")
                .apiProperty("cors","true")
                .component("servlet")
                .dataFormatProperty("prettyPrint","true");
    }
}
