package ru.nikituz.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import ru.nikituz.rest.models.enums.DocumentType;

import java.util.Date;

import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_ISSUE_DATE_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_NUMBER_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_NUMBER_REGEXP;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_SERIES_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_SERIES_REGEXP;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_TYPE_NULL_ERROR_MESSAGE;

@Data
@XmlRootElement(name = "document")
@XmlAccessorType(XmlAccessType.FIELD)
public class DocumentDto {

    @Pattern(regexp = DOCUMENT_SERIES_REGEXP, message = DOCUMENT_SERIES_ERROR_MESSAGE)
    @XmlAttribute(name = "series")
    private String series;

    @Pattern(regexp = DOCUMENT_NUMBER_REGEXP, message = DOCUMENT_NUMBER_ERROR_MESSAGE)
    @XmlAttribute(name = "number")
    private String number;

    @NotNull(message = DOCUMENT_TYPE_NULL_ERROR_MESSAGE)
    @XmlAttribute(name = "type")
    private DocumentType type;

    @NotNull(message = DOCUMENT_ISSUE_DATE_NULL_ERROR_MESSAGE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @XmlAttribute(name = "issueDate")
    private Date issueDate;
}
