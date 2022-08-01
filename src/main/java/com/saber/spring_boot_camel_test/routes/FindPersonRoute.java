package com.saber.spring_boot_camel_test.routes;

import com.saber.spring_boot_camel_test.entities.PersonEntity;
import org.apache.camel.Exchange;
import org.apache.camel.model.rest.RestParamType;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class FindPersonRoute extends AbstractRestRouteBuilder {

    @Override
    public void configure() throws Exception {
        super.configure();

        rest("/person")
                .get("/findByNationalCode/{nationalCode}")
                .description("find person by nationalCode")
                .id(Routes.FIND_PERSON_BY_NationalCode_ROUTE)
                .responseMessage().code(HttpStatus.OK.value()).message(HttpStatus.OK.getReasonPhrase()).responseModel(PersonEntity.class).endResponseMessage()
                .param().name(Headers.nationalCode).type(RestParamType.header).dataType("string").example("0079028748").description("nationalCode").endParam()
                .to(String.format("direct:%s", Routes.FIND_PERSON_BY_NationalCode_ROUTE));

        from(String.format("direct:%s", Routes.FIND_PERSON_BY_NationalCode_ROUTE))
                .routeId(Routes.FIND_PERSON_BY_NationalCode_ROUTE)
                .routeGroup(Routes.FIND_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Request for find person with nationalCode ${in.header.nationalCode} ")
                .validate(header(Headers.nationalCode).isNotNull())
                .validate(header(Headers.nationalCode).regex("\\d{10}"))
                .toD("sql:select * from persons p where p.nationalCode =${in.header.nationalCode}?outputType=selectOne&outputClass=" + PersonEntity.class.getName())
                .to(String.format("direct:%s", Routes.FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY));

        from(String.format("direct:%s", Routes.FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY))
                .routeId(Routes.FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY)
                .routeGroup(Routes.FIND_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Response for find person with nationalCode ${in.header.nationalCode} ==> ${in.body}")
                .choice()
                .when(body().isNull())
                   .to(String.format("direct:%s", Routes.RESOURCE_NOTFOUND_RESPONSE_ROUTE))
                .otherwise()
                  .to(String.format("direct:%s", Routes.FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT))
                .end();

        from(String.format("direct:%s", Routes.FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT))
                .routeId(Routes.FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT)
                .routeGroup(Routes.FIND_PERSON_BY_NationalCode_ROUTE_GROUP)
                .log("Response for find person with nationalCode ${in.header.nationalCode} ==> ${in.body}")
                .setHeader(Exchange.HTTP_RESPONSE_CODE, constant(200));
    }
}