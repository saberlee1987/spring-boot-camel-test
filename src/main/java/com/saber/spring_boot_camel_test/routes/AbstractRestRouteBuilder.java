package com.saber.spring_boot_camel_test.routes;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.saber.spring_boot_camel_test.exceptions.ResourceDuplicationException;
import com.saber.spring_boot_camel_test.exceptions.ResourceNotFoundException;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.bean.validator.BeanValidationException;
import org.apache.camel.support.processor.PredicateValidationException;

public class AbstractRestRouteBuilder extends RouteBuilder {

    @Override
    public void configure() throws Exception {

        onException(ResourceNotFoundException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log("Error for ResourceNotFoundException with error ===> " + exceptionMessage())
                .to(String.format("direct:%s", Routes.RESOURCE_NOTFOUND_EXCEPTION_ROUTE));

        onException(ResourceDuplicationException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log("Error for ResourceDuplicationException with error ===> " + exceptionMessage())
                .to(String.format("direct:%s", Routes.RESOURCE_DUPLICATION_EXCEPTION_ROUTE));

        onException(BeanValidationException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log("Error for BeanValidationException with error ===> " + exceptionMessage())
                .to(String.format("direct:%s", Routes.BEAN_VALIDATION_EXCEPTION_ROUTE));

        onException(PredicateValidationException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log("Error for PredicateValidationException with error ===> " + exceptionMessage())
                .to(String.format("direct:%s", Routes.PREDICATE_VALIDATION_EXCEPTION_ROUTE));

        onException(JsonMappingException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log("Error for JsonMappingException with error ===> " + exceptionMessage())
                .to(String.format("direct:%s", Routes.JSON_MAPPING_EXCEPTION_ROUTE));

        onException(JsonParseException.class)
                .handled(true)
                .maximumRedeliveries(0)
                .log("Error for JsonParseException with error ===> " + exceptionMessage())
                .to(String.format("direct:%s", Routes.JSON_PARSER_EXCEPTION_ROUTE));


        from(String.format("direct:%s", Routes.RESOURCE_NOTFOUND_RESPONSE_ROUTE))
                .routeId(Routes.RESOURCE_NOTFOUND_RESPONSE_ROUTE)
                .routeGroup(Routes.RESOURCE_NOTFOUND_RESPONSE_ROUTE_GROUP)
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode, String.class);
                    String message = String.format("person with nationalCode %s does not exist", nationalCode);
                    throw new ResourceNotFoundException(message);
                });

        from(String.format("direct:%s", Routes.RESOURCE_DUPLICATION_RESPONSE_ROUTE))
                .routeId(Routes.RESOURCE_DUPLICATION_RESPONSE_ROUTE)
                .routeGroup(Routes.RESOURCE_DUPLICATION_RESPONSE_ROUTE_GROUP)
                .process(exchange -> {
                    String nationalCode = exchange.getIn().getHeader(Headers.nationalCode, String.class);
                    String message = String.format("person with nationalCode %s already exist", nationalCode);
                    throw new ResourceDuplicationException(message);
                });

    }
}