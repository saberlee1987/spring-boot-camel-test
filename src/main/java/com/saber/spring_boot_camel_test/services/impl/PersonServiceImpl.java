package com.saber.spring_boot_camel_test.services.impl;

import com.saber.spring_boot_camel_test.dto.person.*;
import com.saber.spring_boot_camel_test.entities.PersonEntity;
import com.saber.spring_boot_camel_test.exceptions.ResourceDuplicationException;
import com.saber.spring_boot_camel_test.exceptions.ResourceNotFoundException;
import com.saber.spring_boot_camel_test.mapper.PersonMapper;
import com.saber.spring_boot_camel_test.repositories.PersonRepository;
import com.saber.spring_boot_camel_test.services.PersonService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.Body;
import org.apache.camel.Header;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service(value = "personService")
@Slf4j
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public AddPersonResponseDto addPerson(@Body PersonDto person) {
        String nationalCode = person.getNationalCode();
        log.info("Request for add person with body {}",person);
        if (this.personRepository.findByNationalCode(nationalCode).isPresent()) {
            log.error("Error for add Person with error {}",String.format("person with nationalCode %s already exist", nationalCode));
            throw new ResourceDuplicationException(String.format("person with nationalCode %s already exist", nationalCode));
        }
        PersonEntity personEntity = personMapper.dtoToModel(person);
        this.personRepository.save(personEntity);
        AddPersonResponseDto responseDto = new AddPersonResponseDto();
        responseDto.setCode(0);
        responseDto.setText("person saved successfully");
        log.info("Response for add person with body {}",responseDto);
        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findByNationalCode(String nationalCode) {
        log.info("Request for find person with nationalCode {}",nationalCode);
        Optional<PersonEntity> optionalPerson = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPerson.isEmpty()) {
            log.error("Error for find person with error {}",String.format("person with nationalCode %s does not exist", nationalCode));
            throw new ResourceNotFoundException(String.format("person with nationalCode %s does not exist", nationalCode));
        }
        PersonEntity personEntity = optionalPerson.get();
        log.info("Response for find person with body {}",personEntity);
        return personMapper.modelToDto(personEntity);
    }

    @Override
    @Transactional
    public DeletePersonResponseDto deleteByNationalCode(@Header(value = "nationalCode") String nationalCode) {
        log.info("Request for delete person with nationalCode {}",nationalCode);
        PersonDto personDto = findByNationalCode(nationalCode);
        this.personRepository.deleteById(personDto.getId());
        DeletePersonResponseDto responseDto = new DeletePersonResponseDto();
        responseDto.setCode(0);
        responseDto.setText("person deleted successfully");
        log.info("Response for delete person with body {}",responseDto);
        return responseDto;
    }

    @Override
    @Transactional
    public UpdatePersonResponseDto updateByNationalCode(@Header(value = "nationalCode") String nationalCode,@Body PersonDto person) {
        log.info("Request for update person with nationalCode {} and body {}",nationalCode,person);
        PersonDto personDto = findByNationalCode(nationalCode);
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setMobile(person.getMobile());
        personDto.setAge(person.getAge());
        this.personRepository.save(personMapper.dtoToModel(personDto));
        UpdatePersonResponseDto responseDto = new UpdatePersonResponseDto();
        responseDto.setCode(0);
        responseDto.setText("person updated successfully");
        log.info("Response for update person with body {}",responseDto);
        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonResponse findAll() {
        List<PersonEntity> entityList = this.personRepository.findAll();
        PersonResponse response = new PersonResponse();
        response.setPersons(personMapper.modelToDto(entityList));
        return response;
    }
}