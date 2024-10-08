package com.saber.spring_boot_camel_test.services;

import com.saber.spring_boot_camel_test.dto.person.*;

public interface PersonService {

    AddPersonResponseDto addPerson(PersonDto person);
    PersonDto findByNationalCode(String nationalCode);
    DeletePersonResponseDto deleteByNationalCode(String nationalCode);
    UpdatePersonResponseDto updateByNationalCode(String nationalCode, PersonDto person);
    PersonResponse findAll();
}
