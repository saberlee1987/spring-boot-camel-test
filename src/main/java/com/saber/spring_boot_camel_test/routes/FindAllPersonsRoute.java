package com.saber.spring_boot_camel_test.routes;

import com.saber.spring_boot_camel_test.dto.person.PersonResponse;
import com.saber.spring_boot_camel_test.entities.PersonEntity;
import org.apache.camel.Exchange;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FindAllPersonsRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/person")
                .get("/findAll")
                .description("find All persons")
                .id(Routes.FIND_ALL_PERSONS_ROUTE)
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonResponse.class).endResponseMessage()
                .to(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE));

        from(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE))
                .routeId(Routes.FIND_ALL_PERSONS_ROUTE)
                .routeGroup(Routes.FIND_ALL_PERSONS_ROUTE_GROUP)
                .log("Request for find All persons ")
                .to("sql:select * from persons ?outputClass=" + PersonEntity.class.getName())
                .to(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY))
                .routeId(Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY)
                .routeGroup(Routes.FIND_ALL_PERSONS_ROUTE_GROUP)
                .marshal().json(JsonLibrary.Jackson)
                .process(exchange -> {
                    String response = exchange.getIn().getBody(String.class);
                    response = String.format("{\"persons\":%s}",response);
                    exchange.getIn().setBody(response);
                })
                .log("Response for find person with nationalCode ${in.header.nationalCode} ==> ${in.body}")
                .to(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT))
                ;

        from(String.format("direct:%s", Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT))
                .routeId(Routes.FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.FIND_ALL_PERSONS_ROUTE_GROUP)
                .unmarshal().json(JsonLibrary.Jackson,PersonResponse.class)
                .log("Response for find All person body ==> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}