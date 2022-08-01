package com.saber.spring_boot_camel_test.routes;

import com.saber.spring_boot_camel_test.dto.person.AddPersonResponseDto;
import com.saber.spring_boot_camel_test.dto.person.PersonDto;
import com.saber.spring_boot_camel_test.services.PersonService;
import org.apache.camel.Exchange;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class AddPersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/person")
                .post("/add")
                .description("Add person")
                .id(Routes.ADD_PERSON_ROUTE)
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).example("example1","{\"firstName\": \"saber\",\"lastName\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"mobile\": \"09365627895\"}").responseModel(AddPersonResponseDto.class).endResponseMessage()
                .type(PersonDto.class)
                .to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE));

        from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE))
                .routeId(Routes.ADD_PERSON_ROUTE)
                .routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
                .to("bean-validator://add-person")
                .log("Request for Add person with nationalCode ${in.header.nationalCode} ")
                .to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY))
                .routeId(Routes.ADD_PERSON_ROUTE_GATEWAY)
                .routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
                .bean(PersonService.class, "addPerson")
                .to(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.ADD_PERSON_ROUTE_GATEWAY_OUT))
                .routeId(Routes.ADD_PERSON_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.ADD_PERSON_ROUTE_GROUP)
                .log("Response for Add person with body ==> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));

    }
}