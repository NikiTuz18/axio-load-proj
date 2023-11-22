package ru.nikituz.rest.services;

import ru.nikituz.rest.dto.PersonDto;

public interface PersonService {
    void create(PersonDto personDto);
    String sendToSoap(PersonDto personDto);
}
