package ru.nikituz.rest.dto;

import lombok.Data;
import ru.nikituz.rest.models.enums.Gender;

import java.sql.Date;

@Data
public class PersonDto {

    private String name;

    private String surname;

    private String patronymic;

    private Date birthDate;

    private Gender gender;

    private DocumentDto document;
}
