package com.saber.spring_boot_camel_test.routes;

import com.saber.spring_boot_camel_test.entities.PersonEntity;
import com.saber.spring_boot_camel_test.services.PersonService;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class DeletePersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/person")
                .delete("/deleteByNationalCode/{nationalCode}")
                .description("Delete person by nationalCode")
                .id(Routes.DELETE_PERSON_BY_NationalCode_ROUTE)
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonEntity.class).endResponseMessage()
                .param().name(Headers.nationalCode).type(RestParamType.header).dataType("string").example("0079028748").description("nationalCode").endParam()
                .to(String.format("direct:%s", Routes.DELETE_PERSON_BY_NationalCode_ROUTE));

        from(String.format("direct:%s", Routes.DELETE_PERSON_BY_NationalCode_ROUTE))
                .routeId(Routes.DELETE_PERSON_BY_NationalCode_ROUTE)
                .routeGroup(Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Request for Delete person with nationalCode ${in.header.nationalCode} ")
                .validate(header(Headers.nationalCode).isNotNull())
                .validate(header(Headers.nationalCode).regex("\\d{10}"))
                .bean(PersonService.class,"deleteByNationalCode")
                .to(String.format("direct:%s", Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY))
                .routeId(Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY)
                .routeGroup(Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Response for Delete person with nationalCode ${in.header.nationalCode} ==> ${in.body}")
               .to(String.format("direct:%s", Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT));

        from(String.format("direct:%s", Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT))
                .routeId(Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.DELETE_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Response for Delete person with nationalCode ${in.header.nationalCode} ==> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}