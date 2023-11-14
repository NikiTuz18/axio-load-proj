package ru.nikituz.rest.repositories;

import ru.nikituz.rest.models.Person;

public interface PersonRepository {
    void save(Person person);
}
