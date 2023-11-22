package ru.nikituz.rest.services;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import ru.nikituz.rest.dto.PersonDto;

public interface PersonService {
    void create(PersonDto personDto);
    ResponseEntity<String> responseFromPost(PersonDto personDto, BindingResult bindingResult);
    String sendToSoap(PersonDto personDto);
}
