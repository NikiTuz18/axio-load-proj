package ru.nikituz.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.nikituz.rest.models.enums.Gender;

import java.util.Date;

import static ru.nikituz.rest.utils.validation.ValidationUtil.BIRTH_DATE_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.GENDER_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.NAME_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.NSP_REGEXP;
import static ru.nikituz.rest.utils.validation.ValidationUtil.PATRONYMIC_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.SURNAME_ERROR_MESSAGE;

@Data
@XmlRootElement(name = "person")
@XmlAccessorType(XmlAccessType.FIELD)
public class PersonDto {

    @Pattern(regexp = NSP_REGEXP, message = NAME_ERROR_MESSAGE)
    @XmlAttribute(name = "name")
    private String name;

    @Pattern(regexp = NSP_REGEXP, message = SURNAME_ERROR_MESSAGE)
    @XmlAttribute(name = "surname")
    private String surname;

    @Pattern(regexp = NSP_REGEXP, message = PATRONYMIC_ERROR_MESSAGE)
    @XmlAttribute(name = "patronymic")
    private String patronymic;

    @NotNull(message = BIRTH_DATE_NULL_ERROR_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @XmlAttribute(name = "birthDate")
    private Date birthDate;

    @NotNull(message = GENDER_NULL_ERROR_MESSAGE)
    @XmlAttribute(name = "gender")
    private Gender gender;

    @NotNull(message = DOCUMENT_NULL_ERROR_MESSAGE)
    @XmlElement(name = "document")
    private DocumentDto document;
}
