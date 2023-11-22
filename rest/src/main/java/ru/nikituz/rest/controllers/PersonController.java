package ru.nikituz.rest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nikituz.rest.dto.PersonDto;
import ru.nikituz.rest.services.PersonService;
import ru.nikituz.rest.utils.exceptions.PersonCreateException;
import ru.nikituz.rest.utils.response.ErrorPersonResponseTransfer;

@RestController
@RequestMapping("/rest/persons")
@RequiredArgsConstructor
@Slf4j
public class PersonController {

    private final PersonService personService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<String> add(@RequestBody @Valid PersonDto personDto, BindingResult bindingResult){
        log.info("Обращение к REST-сервису..");
        return personService.responseFromPost(personDto, bindingResult);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorPersonResponseTransfer> handlePersonException(PersonCreateException exception){
        log.error("Ошибка конвертации при вызове SOAP-модуля:" + exception.getMessage());
        return new ResponseEntity<>(
                new ErrorPersonResponseTransfer(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
