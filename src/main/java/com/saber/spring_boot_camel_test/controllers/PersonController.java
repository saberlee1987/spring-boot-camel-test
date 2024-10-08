package com.saber.spring_boot_camel_test.controllers;

import com.saber.spring_boot_camel_test.dto.ErrorResponseDto;
import com.saber.spring_boot_camel_test.dto.person.*;
import com.saber.spring_boot_camel_test.services.PersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;

@RestController
@RequestMapping(value = "${service.api.springboot.base-path}/person", produces = MediaType.APPLICATION_JSON)
@Tag(name = "person", description = "person service")
@RequiredArgsConstructor
@Validated
public class PersonController {

    private final PersonService personService;

    @PostMapping(value = "/add", consumes = MediaType.APPLICATION_JSON)
    @Operation(summary = "add person", description = "add Person",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    description = "person information for add", content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(value = "{\"firstName\": \"saber\",\"lastName\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"mobile\": \"09365627895\"}")))
            , responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = AddPersonResponseDto.class))}),
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
    public ResponseEntity<AddPersonResponseDto> addPerson(@RequestBody @Valid PersonDto personEntity) {
        AddPersonResponseDto responseDto = this.personService.addPerson(personEntity);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/findByNationalCode/{nationalCode}")
    @Operation(summary = "find person by nationalCode", description = "find Person by nationalCode",
            parameters = {
                    @Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748")
            }
            , responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonDto.class))}),
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
    public ResponseEntity<PersonDto> findPersonByNationalCode(@PathVariable(value = "nationalCode") String nationalCode) {
        PersonDto responseDto = this.personService.findByNationalCode(nationalCode);
        return ResponseEntity.ok(responseDto);
    }

    @PutMapping(value = "/updateByNationalCode/{nationalCode}", consumes = MediaType.APPLICATION_JSON)
    @Operation(summary = "update person by nationalCode", description = "update Person by nationalCode",
            parameters = {
                    @Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748")
            }
            ,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    description = "person information for update", content = @Content(mediaType = MediaType.APPLICATION_JSON,
                    examples = @ExampleObject(value = "{\"firstName\": \"saber\",\"lastName\": \"Azizi\",\"nationalCode\": \"0079028748\",\"age\": 34,\"mobile\": \"09365627895\"}")))
            ,
            responses = {
                    @ApiResponse(responseCode = "200", description = "Success",
                            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UpdatePersonResponseDto.class))}),
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
    public ResponseEntity<UpdatePersonResponseDto> updatePersonByNationalCode(@PathVariable(value = "nationalCode") String nationalCode, @RequestBody @Valid PersonDto person) {
        UpdatePersonResponseDto responseDto = this.personService.updateByNationalCode(nationalCode, person);
        return ResponseEntity.ok(responseDto);
    }


    @DeleteMapping(value = "/deleteByNationalCode/{nationalCode}")
    @Operation(summary = "delete person by nationalCode", description = "delete Person by nationalCode",
            parameters = {
                    @Parameter(name = "nationalCode", in = ParameterIn.PATH, required = true, example = "0079028748")
            }
            , responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = DeletePersonResponseDto.class))}),
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
    public ResponseEntity<DeletePersonResponseDto> deletePersonByNationalCode(@PathVariable(value = "nationalCode") String nationalCode) {
        DeletePersonResponseDto responseDto = this.personService.deleteByNationalCode(nationalCode);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping(value = "/findAll")
    @Operation(summary = "findAll persons", description = "findAll persons"
            , responses = {
            @ApiResponse(responseCode = "200", description = "Success",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = PersonResponse.class))}),
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
    public ResponseEntity<PersonResponse> findAll() {
        PersonResponse response = this.personService.findAll();
        return ResponseEntity.ok(response);
    }
}