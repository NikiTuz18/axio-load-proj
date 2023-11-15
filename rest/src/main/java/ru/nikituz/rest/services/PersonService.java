package ru.nikituz.rest.services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.nikituz.rest.dto.PersonDto;

public interface PersonService {
    void create(PersonDto personDto);
    ResponseEntity<HttpStatus> ResponseFromPost(PersonDto personDto, BindingResult bindingResult);
}
