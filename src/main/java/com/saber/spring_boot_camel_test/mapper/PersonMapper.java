package com.saber.spring_boot_camel_test.mapper;

import com.saber.spring_boot_camel_test.dto.person.PersonDto;
import com.saber.spring_boot_camel_test.entities.PersonEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper
public interface PersonMapper {

    PersonEntity dtoToModel(PersonDto personDto);

    PersonDto modelToDto(PersonEntity personEntity);
    List<PersonDto> modelToDto(List<PersonEntity> personEntities);

}
