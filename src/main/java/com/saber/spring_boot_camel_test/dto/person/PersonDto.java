package com.saber.spring_boot_camel_test.dto.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;

@Data
public class PersonDto {
    @JsonIgnore
    private Integer id;
    @NotBlank(message = "firstName is Required")
    @ApiModelProperty(name = "firstName",example = "saber")
    private String firstName;
    @NotBlank(message = "lastName is Required")
    @ApiModelProperty(name = "lastName",example = "Azizi")
    private String lastName;
    @Positive(message = "age must be > 0")
    @Max(value = 200,message = "maximum age must be < 200")
    @ApiModelProperty(name = "age",example = "35")
    private Integer age;
    @NotBlank(message = "mobile is Required")
    @Pattern(regexp = "09[0-9]{9}",message = "mobile is invalid")
    @ApiModelProperty(name = "mobile",example = "09365627895")
    private String mobile;
    @NotBlank(message = "nationalCode is Required")
    @Pattern(regexp = "\\d{10}",message = "nationalCode must be 10 digit")
    @ApiModelProperty(name = "nationalCode",example = "0079028748")
    private String nationalCode;
    @NotBlank(message = "email is Required")
    @Email(message = "email is invalid")
    @ApiModelProperty(name = "email",example = "saberazizi66@yahoo.com")
    private String email;
}
