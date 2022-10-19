package com.saber.spring_boot_camel_test.routes;

public interface Routes {
    String SAY_HELLO_ROUTE = "say-hello-route" ;
    String SAY_HELLO_ROUTE_GATEWAY = "say-hello-route-gateway" ;
    String SAY_HELLO_ROUTE_GROUP = "say-hello-route-group" ;

    String SAY_HELLO_POST_ROUTE = "say-hello-post-route" ;
    String SAY_HELLO_POST_ROUTE_GATEWAY = "say-hello-post-route-gateway" ;
    String SAY_HELLO_POST_ROUTE_GROUP = "say-hello-post-route-group" ;


    String FIND_PERSON_BY_NationalCode_ROUTE = "find-person-by-nationalCode-route" ;
    String FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY = "find-person-by-nationalCode-route-gateway" ;
    String FIND_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT = "find-person-by-nationalCode-route-gateway-out" ;
    String FIND_PERSON_BY_NationalCode_ROUTE_GROUP = "find-person-by-nationalCode-route-group" ;


    String FIND_ALL_PERSONS_ROUTE = "find-all-persons-route" ;
    String FIND_ALL_PERSONS_ROUTE_GATEWAY = "find-all-persons-route-gateway" ;
    String FIND_ALL_PERSONS_ROUTE_GATEWAY_OUT = "find-all-persons-route-gateway-out" ;
    String FIND_ALL_PERSONS_ROUTE_GROUP = "find-all-persons-route-group" ;

    String DELETE_PERSON_BY_NationalCode_ROUTE = "delete-person-by-nationalCode-route" ;
    String DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY = "delete-person-by-nationalCode-route-gateway" ;
    String DELETE_PERSON_BY_NationalCode_ROUTE_GATEWAY_OUT = "delete-person-by-nationalCode-route-gateway-out" ;
    String DELETE_PERSON_BY_NationalCode_ROUTE_GROUP = "delete-person-by-nationalCode-route-group" ;

    String UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE = "update-person-by-nationalCode-route" ;
    String UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY = "update-person-by-nationalCode-route-gateway" ;
    String UPDATE_PERSON_BY_NATIONAL_CODE_ROUTE_GATEWAY_OUT = "update-person-by-nationalCode-route-gateway-out" ;
    String UPDATE_PERSON_BY_NationalCode_ROUTE_GROUP = "update-person-by-nationalCode-route-group" ;

    String ADD_PERSON_ROUTE = "add-person-route" ;
    String ADD_PERSON_ROUTE_GATEWAY = "add-person-route-gateway" ;
    String ADD_PERSON_ROUTE_GATEWAY_OUT = "add-person-route-gateway-out" ;
    String ADD_PERSON_ROUTE_GROUP = "add-person-route-group" ;

    String RESOURCE_NOTFOUND_RESPONSE_ROUTE = "resource-notfound-response-route" ;
    String RESOURCE_NOTFOUND_RESPONSE_ROUTE_GROUP = "resource-notfound-response-route_group" ;

    String RESOURCE_DUPLICATION_RESPONSE_ROUTE = "resource-duplication-response-route" ;
    String RESOURCE_DUPLICATION_RESPONSE_ROUTE_GROUP = "resource-duplication-response-route_group" ;


    String RESOURCE_NOTFOUND_EXCEPTION_ROUTE = "resource-notfound-exception-route" ;
    String RESOURCE_DUPLICATION_EXCEPTION_ROUTE = "resource-duplication-exception-route" ;
    String BEAN_VALIDATION_EXCEPTION_ROUTE = "bean-validation-exception-route" ;
    String PREDICATE_VALIDATION_EXCEPTION_ROUTE = "predicate-validation-exception-route" ;
    String JSON_MAPPING_EXCEPTION_ROUTE = "json-mapping-exception-route" ;
    String JSON_PARSER_EXCEPTION_ROUTE = "json-parser-exception-route" ;



    String EXCEPTION_HANDLER_ROUTE_GROUP = "exception-handler-route-group";
}
