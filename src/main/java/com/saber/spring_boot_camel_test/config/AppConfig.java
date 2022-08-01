package com.saber.spring_boot_camel_test.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.saber.spring_boot_camel_test.dto.ErrorResponseDto;
import io.micrometer.core.instrument.Tag;
import io.micrometer.core.instrument.Tags;
import org.apache.camel.CamelContext;
import org.apache.camel.component.metrics.routepolicy.MetricsRoutePolicyFactory;
import org.apache.camel.component.micrometer.messagehistory.MicrometerMessageHistoryFactory;
import org.apache.camel.component.micrometer.routepolicy.MicrometerRoutePolicyFactory;
import org.apache.camel.model.rest.RestBindingMode;
import org.apache.camel.model.rest.RestDefinition;
import org.apache.camel.spring.SpringCamelContext;
import org.apache.camel.spring.boot.CamelContextConfiguration;
import org.springframework.boot.actuate.metrics.web.servlet.DefaultWebMvcTagsProvider;
import org.springframework.boot.actuate.metrics.web.servlet.WebMvcTagsProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;

@Configuration
public class AppConfig {

    @Bean
    public ObjectMapper mapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.FAIL_ON_UNWRAPPED_TYPE_IDENTIFIERS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNRESOLVED_OBJECT_IDS, false);
        mapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        mapper.configure(DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_EMPTY);
        return mapper;
    }


    @Bean
    public CamelContextConfiguration camelConfig() {
        return new CamelContextConfiguration() {
            @Override
            public void beforeApplicationStart(CamelContext camelContext) {

                camelContext.addRoutePolicyFactory(new MicrometerRoutePolicyFactory());
                camelContext.addRoutePolicyFactory(new MetricsRoutePolicyFactory());
                camelContext.setMessageHistoryFactory(new MicrometerMessageHistoryFactory());
            }

            @Override
            public void afterApplicationStart(CamelContext camelContext) {
                SpringCamelContext springCamelContext = (SpringCamelContext) camelContext;
                for (RestDefinition restDefinition : springCamelContext.getRestDefinitions()) {

                    restDefinition
                            .bindingMode(RestBindingMode.json)
                            .enableCORS(true)
                            .produces(MediaType.APPLICATION_JSON)

                            .responseMessage().code(400).message(HttpStatus.BAD_REQUEST.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage()
                            .responseMessage().code(401).message(HttpStatus.UNAUTHORIZED.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage()
                            .responseMessage().code(403).message(HttpStatus.FORBIDDEN.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage()
                            .responseMessage().code(404).message(HttpStatus.NOT_FOUND.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage()
                            .responseMessage().code(406).message(HttpStatus.NOT_ACCEPTABLE.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage()
                            .responseMessage().code(500).message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage()
                            .responseMessage().code(503).message(HttpStatus.SERVICE_UNAVAILABLE.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage()
                            .responseMessage().code(504).message(HttpStatus.GATEWAY_TIMEOUT.getReasonPhrase()).responseModel(ErrorResponseDto.class).endResponseMessage();
                }
            }
        };
    }

    @Bean
    WebMvcTagsProvider webMvcTagsProvider() {
        return new DefaultWebMvcTagsProvider() {
            @Override
            public Iterable<Tag> getTags(HttpServletRequest request, HttpServletResponse response,
                                         Object handler, Throwable exception) {
                return Tags.concat(
                        super.getTags(request, response, handler, exception),
                        Tags.of(Tag.of("uri", request.getRequestURI()))
                );
            }
        };
    }
}