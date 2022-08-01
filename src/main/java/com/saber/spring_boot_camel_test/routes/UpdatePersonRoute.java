package com.saber.spring_boot_camel_test.routes;

import com.saber.spring_boot_camel_test.dto.person.PersonDto;
import com.saber.spring_boot_camel_test.entities.PersonEntity;
import com.saber.spring_boot_camel_test.services.PersonService;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class UpdatePersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/person")
                .put("/updateByNationalCode/{nationalCode}")
                .description("Update person by nationalCode")
                .id(Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE)
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonEntity.class).endResponseMessage()
                .param().name(Headers.nationalCode).type(RestParamType.header).dataType("string").example("0079028748").description("nationalCode").endParam()
                .type(PersonDto.class)
                .to(String.format("direct:%s", Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE));

        from(String.format("direct:%s", Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE))
                .routeId(Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE)
                .routeGroup(Routes.UPDATE_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Request for Update person with nationalCode ${in.header.nationalCode} with body ===> ${in.body} ")
                .to("bean-validator://update-person")
                .validate(header(Headers.nationalCode).isNotNull())
                .validate(header(Headers.nationalCode).regex("\\d{10}"))
                .bean(PersonService.class,"updateByNationalCode")
                .to(String.format("direct:%s", Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY))
                .routeId(Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY)
                .routeGroup(Routes.UPDATE_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Response for Update person with nationalCode ${in.header.nationalCode} ==> ${in.body}")
               .to(String.format("direct:%s", Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT))
                .routeId(Routes.UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.UPDATE_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Response for Update person with nationalCode ${in.header.nationalCode} ==> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}