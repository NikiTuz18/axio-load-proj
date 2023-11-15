package ru.nikituz.rest.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nikituz.rest.dto.PersonDto;
import ru.nikituz.rest.services.PersonService;

@RestController
@RequestMapping("/persons")
@RequiredArgsConstructor
public class PersonController {

    private final PersonService personService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity<HttpStatus> add(@RequestBody @Valid PersonDto personDto, BindingResult bindingResult){
        return personService.ResponseFromPost(personDto, bindingResult);
    }
}
