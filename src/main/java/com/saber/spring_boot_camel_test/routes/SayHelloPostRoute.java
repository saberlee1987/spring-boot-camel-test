package com.saber.spring_boot_camel_test.routes;

import com.saber.spring_boot_camel_test.dto.hi.HelloDto;
import com.saber.spring_boot_camel_test.dto.hi.HelloRequestDto;
import com.saber.spring_boot_camel_test.services.HelloService;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

@Component
public class SayHelloPostRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/hello")
                .post("/sayHello")
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(HelloDto.class).endResponseMessage()
                .type(HelloRequestDto.class)
                .to(String.format("direct:%s", Routes.SAY_HELLO_POST_ROUTE));

        from(String.format("direct:%s", Routes.SAY_HELLO_POST_ROUTE))
                .routeId(Routes.SAY_HELLO_POST_ROUTE)
                .routeGroup(Routes.SAY_HELLO_POST_ROUTE_GROUP)
                .log("Request for say Hello with body ===> ${in.body}")
                .bean(HelloService.class, "sayHelloPost")
                .to(String.format("direct:%s", Routes.SAY_HELLO_POST_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.SAY_HELLO_POST_ROUTE_GATEWAY))
                .routeId(Routes.SAY_HELLO_POST_ROUTE_GATEWAY)
                .routeGroup(Routes.SAY_HELLO_POST_ROUTE_GROUP)
                .log("Response for say Hello ===> ${in.body}")
                .setHeader(Exchange.CONTENT_TYPE,constant(StandardCharsets.ISO_8859_1))
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}
