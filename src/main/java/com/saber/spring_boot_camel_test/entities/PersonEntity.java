package com.saber.spring_boot_camel_test.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.saber.spring_boot_camel_test.helper.PersianDateJackson;
import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.LocalDateTime;

@Entity
@Table(name = "persons")
@Data
public class PersonEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY    )
    @JsonIgnore
    private Integer id;
    @Column(name = "firstName",length = 75)
    @NotBlank(message = "firstName is Required")
    private String firstName;
    @Column(name = "lastName",length = 85)
    @NotBlank(message = "lastName is Required")
    private String lastName;
    @Column(name = "nationalCode",length = 10,unique = true)
    @NotBlank(message = "nationalCode is Required")
    @Pattern(regexp = "\\d{10}",message = "nationalCode must be 10 digit")
    private String nationalCode;
    @Column(name = "age")
    @Positive(message = "age must be > 0")
    @Max(value = 200,message = "maximum age must be < 200")
    private Integer age;
    @Column(name = "mobile",length = 11)
    @NotBlank(message = "mobile is Required")
    @Pattern(regexp = "09[0-9]{9}",message = "mobile is invalid")
    private String mobile;
    @Column(name = "email")
    private String email;
    @Column(name = "createdAt",length = 50)
    @JsonSerialize(using = PersianDateJackson.SerializerLocalDateTime.class)
    private LocalDateTime createdAt;
    @Column(name = "updatedAt",length = 50)
    @JsonSerialize(using = PersianDateJackson.SerializerLocalDateTime.class)
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }
    @PreUpdate
    public void preUpdate(){
        this.updatedAt = LocalDateTime.now();
    }

}
