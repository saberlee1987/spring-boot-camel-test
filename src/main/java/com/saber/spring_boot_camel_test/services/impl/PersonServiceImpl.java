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
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import java.awt.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
        log.info("Request for add person with body {}", person);
        if (this.personRepository.findByNationalCode(nationalCode).isPresent()) {
            log.error("Error for add Person with error {}", String.format("person with nationalCode %s already exist", nationalCode));
            throw new ResourceDuplicationException(String.format("person with nationalCode %s already exist", nationalCode));
        }
        PersonEntity personEntity = personMapper.dtoToModel(person);
        this.personRepository.save(personEntity);
        AddPersonResponseDto responseDto = new AddPersonResponseDto();
        responseDto.setCode(0);
        responseDto.setText("person saved successfully");
        log.info("Response for add person with body {}", responseDto);
        return responseDto;
    }

    @Override
    @Transactional(readOnly = true)
    public PersonDto findByNationalCode(String nationalCode) {
        log.info("Request for find person with nationalCode {}", nationalCode);
        Optional<PersonEntity> optionalPerson = this.personRepository.findByNationalCode(nationalCode);
        if (optionalPerson.isEmpty()) {
            log.error("Error for find person with error {}", String.format("person with nationalCode %s does not exist", nationalCode));
            throw new ResourceNotFoundException(String.format("person with nationalCode %s does not exist", nationalCode));
        }
        PersonEntity personEntity = optionalPerson.get();
        log.info("Response for find person with body {}", personEntity);
        return personMapper.modelToDto(personEntity);
    }

    @Override
    @Transactional
    public DeletePersonResponseDto deleteByNationalCode(@Header(value = "nationalCode") String nationalCode) {
        log.info("Request for delete person with nationalCode {}", nationalCode);
        PersonDto personDto = findByNationalCode(nationalCode);
        this.personRepository.deleteById(personDto.getId());
        DeletePersonResponseDto responseDto = new DeletePersonResponseDto();
        responseDto.setCode(0);
        responseDto.setText("person deleted successfully");
        log.info("Response for delete person with body {}", responseDto);
        return responseDto;
    }

    @Override
    @Transactional
    public UpdatePersonResponseDto updateByNationalCode(@Header(value = "nationalCode") String nationalCode, @Body PersonDto person) {
        log.info("Request for update person with nationalCode {} and body {}", nationalCode, person);
        PersonDto personDto = findByNationalCode(nationalCode);
        personDto.setFirstName(person.getFirstName());
        personDto.setLastName(person.getLastName());
        personDto.setMobile(person.getMobile());
        personDto.setAge(person.getAge());
        this.personRepository.save(personMapper.dtoToModel(personDto));
        UpdatePersonResponseDto responseDto = new UpdatePersonResponseDto();
        responseDto.setCode(0);
        responseDto.setText("person updated successfully");
        log.info("Response for update person with body {}", responseDto);
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

    @Override
    public void exportPersonToExcel(HttpServletResponse response) {
        List<PersonEntity> entityList = this.personRepository.findAll();
        if (!entityList.isEmpty()) {
            try (HSSFWorkbook workbook = new HSSFWorkbook();
                 ServletOutputStream outputStream = response.getOutputStream()) {
                HSSFFont font = workbook.createFont();
                font.setColor((short) 0);

                HSSFColor color = new HSSFColor(0, 0, Color.BLACK);
                HSSFCellStyle cellStyle = workbook.createCellStyle();
                cellStyle.setFont(font);
                cellStyle.setAlignment(HorizontalAlignment.CENTER);
                cellStyle.setWrapText(false);
                cellStyle.setFillForegroundColor(color);
                cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
                cellStyle.setShrinkToFit(true);

                HSSFSheet sheet = workbook.createSheet("مشخصات افراد داخل دیتابیس");
                sheet.setRightToLeft(true);
                int rowIndex = 0;
                HSSFRow row = sheet.createRow(rowIndex);
                rowIndex += 1;
                List<String> columnTitles = List.of(
                        "شناسه", "نام", "نام خانوادگی", "کد ملی"
                        , "سن", "شماره همراه", "آدرس پست الکترونیکی",
                        "تاریخ ایجاد", "تاریخ به روز رسانی"
                );
                HSSFCell cell;
                for (int i = 0; i < columnTitles.size(); i++) {
                    cell = row.createCell(i, CellType.STRING);
                    cell.setCellStyle(cellStyle);
                    cell.setCellValue(columnTitles.get(i));
                }
                PersonEntity personEntity;
                Object object;
                for (PersonEntity entity : entityList) {
                    row = sheet.createRow(rowIndex);

                    personEntity = entity;
                    for (int j = 0; j < personEntity.personFields().size(); j++) {
                        cell = row.createCell(j, CellType.STRING);
                        cell.setCellStyle(cellStyle);
                        object = personEntity.personFields().get(j);
                        cell.setCellValue(object.toString());
                     }
                    rowIndex += 1;
                }
                String headerKey = "Content-Disposition";
                String headerValue = "attachment; filename=persons_"+ UUID.randomUUID() +".xls";
                response.setHeader(headerKey, headerValue);
                response.setContentType(MediaType.APPLICATION_OCTET_STREAM);
                workbook.write(outputStream);
                log.info("excel file write successfully ...........");
            } catch (Exception e) {
                e.printStackTrace();
                throw new RuntimeException("can not write persons to excel ", e);
            }
        }
    }
}