package ru.nikituz.rest.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nikituz.rest.dto.PersonDto;
import ru.nikituz.rest.models.Person;
import ru.nikituz.rest.repositories.PersonRepository;
import ru.nikituz.rest.services.PersonService;
import ru.nikituz.rest.utils.exceptions.PersonCreateException;
import ru.nikituz.rest.utils.mappers.PersonMapper;
import ru.nikituz.rest.utils.response.ErrorPersonResponseTransfer;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    @Override
    @Transactional
    public void create(PersonDto personDto) {
        Person person = personMapper.toModel(personDto);
        personRepository.save(person);
    }

    @Override
    @Transactional
    public ResponseEntity<HttpStatus> ResponseFromPost(PersonDto personDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";\n");
            }

            throw new PersonCreateException(errorMessage.toString());
        }

        create(personDto);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @ExceptionHandler
    private ResponseEntity<ErrorPersonResponseTransfer> handlePersonException(PersonCreateException exception){
        return new ResponseEntity<>(
                new ErrorPersonResponseTransfer(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    private String parseToXml(PersonDto personDto) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        return xmlMapper.writeValueAsString(personDto);
    }
}
