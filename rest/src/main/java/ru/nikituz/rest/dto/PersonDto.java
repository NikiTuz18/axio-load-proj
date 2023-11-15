package ru.nikituz.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.nikituz.rest.models.enums.Gender;

import java.sql.Date;

import static ru.nikituz.rest.utils.validation.ValidationUtil.BIRTH_DATE_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.GENDER_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.NAME_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.NSP_REGEXP;
import static ru.nikituz.rest.utils.validation.ValidationUtil.PATRONYMIC_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.SURNAME_ERROR_MESSAGE;

@Data
public class PersonDto {

    @Pattern(regexp = NSP_REGEXP, message = NAME_ERROR_MESSAGE)
    private String name;

    @Pattern(regexp = NSP_REGEXP, message = SURNAME_ERROR_MESSAGE)
    private String surname;

    @Pattern(regexp = NSP_REGEXP, message = PATRONYMIC_ERROR_MESSAGE)
    private String patronymic;

    @NotNull(message = BIRTH_DATE_NULL_ERROR_MESSAGE)
    private Date birthDate;

    @NotNull(message = GENDER_NULL_ERROR_MESSAGE)
    private Gender gender;

    @NotNull(message = DOCUMENT_NULL_ERROR_MESSAGE)
    private DocumentDto document;
}
