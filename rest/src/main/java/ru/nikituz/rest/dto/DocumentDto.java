package ru.nikituz.rest.dto;

import lombok.Data;
import ru.nikituz.rest.models.enums.DocumentType;

import java.sql.Date;

@Data
public class DocumentDto {

    private String series;

    private String number;

    private Date issueDate;

    private DocumentType type;
}
