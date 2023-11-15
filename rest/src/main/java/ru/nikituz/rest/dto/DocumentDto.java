package ru.nikituz.rest.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import ru.nikituz.rest.models.enums.DocumentType;

import java.sql.Date;

import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_ISSUE_DATE_NULL_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_NUMBER_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_NUMBER_REGEXP;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_SERIES_ERROR_MESSAGE;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_SERIES_REGEXP;
import static ru.nikituz.rest.utils.validation.ValidationUtil.DOCUMENT_TYPE_NULL_ERROR_MESSAGE;

@Data
public class DocumentDto {

    @Pattern(regexp = DOCUMENT_SERIES_REGEXP, message = DOCUMENT_SERIES_ERROR_MESSAGE)
    private String series;

    @Pattern(regexp = DOCUMENT_NUMBER_REGEXP, message = DOCUMENT_NUMBER_ERROR_MESSAGE)
    private String number;

    @NotNull(message = DOCUMENT_TYPE_NULL_ERROR_MESSAGE)
    private DocumentType type;

    @NotNull(message = DOCUMENT_ISSUE_DATE_NULL_ERROR_MESSAGE)
    private Date issueDate;
}
