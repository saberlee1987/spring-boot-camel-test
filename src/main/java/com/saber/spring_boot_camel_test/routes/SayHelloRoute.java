package com.saber.spring_boot_camel_test.routes;

import com.saber.spring_boot_camel_test.dto.hi.HelloDto;
import com.saber.spring_boot_camel_test.services.HelloService;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class SayHelloRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/hello")
                .get("/sayHello")
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(HelloDto.class).endResponseMessage()
                .param().name(Headers.firstName).type(RestParamType.query).dataType("string").example("saber").description("firstName").endParam()
                .param().name(Headers.lastName).type(RestParamType.query).dataType("string").example("Azizi").description("lastName").endParam()
                .to(String.format("direct:%s", Routes.SAY_HELLO_ROUTE));

        from(String.format("direct:%s", Routes.SAY_HELLO_ROUTE))
                .routeId(Routes.SAY_HELLO_ROUTE)
                .routeGroup(Routes.SAY_HELLO_ROUTE_GROUP)
                .log("Request for say Hello with parameter firstName ${in.header.firstName} , lastName : ${in.header.lastName}")
                .bean(HelloService.class, "sayHello")
                .to(String.format("direct:%s", Routes.SAY_HELLO_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.SAY_HELLO_ROUTE_GATEWAY))
                .routeId(Routes.SAY_HELLO_ROUTE_GATEWAY)
                .routeGroup(Routes.SAY_HELLO_ROUTE_GROUP)
                .log("Response for say Hello ===> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}
