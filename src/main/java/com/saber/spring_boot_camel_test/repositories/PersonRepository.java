package com.saber.spring_boot_camel_test.repositories;

import com.saber.spring_boot_camel_test.entities.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonEntity,Integer> {

    Optional<PersonEntity> findByNationalCode(String nationalCode);
}
