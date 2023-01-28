package com.saber.spring_boot_camel_test.controllers;

import com.saber.spring_boot_camel_test.dto.ErrorResponseDto;
import com.saber.spring_boot_camel_test.dto.hi.HelloDto;
import com.saber.spring_boot_camel_test.routes.Headers;
import com.saber.spring_boot_camel_test.services.HelloService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(value = "${service.api.springboot.base-path}/hello", produces = MediaType.APPLICATION_JSON)
@Tag(name = "hello", description = "hello service")
@RequiredArgsConstructor
@Validated
public class HelloController {

    private final HelloService helloService;

    @GetMapping(value = "/sayHello")
    @Operation(summary = "sayHello", description = "say Hello",
            parameters = {
                    @Parameter(name = Headers.firstName, in = ParameterIn.QUERY, required = true, example = "saber"),
                    @Parameter(name = Headers.lastName, in = ParameterIn.QUERY, required = true, example = "Azizi")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = HelloDto.class))}),
                    @ApiResponse(responseCode = "400", description = "BAD_REQUEST",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
                    @ApiResponse(responseCode = "401", description = "UNAUTHORIZED",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
                    @ApiResponse(responseCode = "403", description = "FORBIDDEN",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
                    @ApiResponse(responseCode = "404", description = "NOT_FOUND",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
                    @ApiResponse(responseCode = "406", description = "NOT_ACCEPTABLE",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
                    @ApiResponse(responseCode = "500", description = "INTERNAL_SERVER_ERROR",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
                    @ApiResponse(responseCode = "503", description = "SERVICE_UNAVAILABLE",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
                    @ApiResponse(responseCode = "504", description = "GATEWAY_TIMEOUT",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponseDto.class))}),
            })
    public ResponseEntity<HelloDto> sayHello(@RequestParam(value = "firstName") @NotBlank(message = "firstName is Required") String firstName, @RequestParam(value = "lastName")@NotBlank(message = "lastName is Required") String lastName) {
        HelloDto helloDto = this.helloService.sayHelloGet(firstName, lastName);
        return ResponseEntity.ok(helloDto);
    }
}
