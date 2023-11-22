package ru.nikituz.rest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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

import java.util.List;

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
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";\n");
            }
            log.error("Ошибка в корректности данных JSON-модели. {}", errorMessage);
            throw new PersonCreateException(errorMessage.toString());
        }

        personService.create(personDto);
        log.info("Данные успешно добавлены в БД (json)!");
        log.info("Обращение к SOAP-модулю..");
        String xmlText = personService.sendToSoap(personDto);
        log.info("SOAP-модуль успешно завершил конвертацию и вернул ответ!");
        return new ResponseEntity<>(xmlText, HttpStatus.OK);
    }


    @ExceptionHandler
    private ResponseEntity<ErrorPersonResponseTransfer> handlePersonException(PersonCreateException exception){
        log.error("Ошибка конвертации при вызове SOAP-модуля: {}", exception.getMessage(), exception);
        return new ResponseEntity<>(
                new ErrorPersonResponseTransfer(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }
}
