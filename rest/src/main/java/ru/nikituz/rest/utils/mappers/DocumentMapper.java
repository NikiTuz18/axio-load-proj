package ru.nikituz.rest.utils.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nikituz.rest.dto.DocumentDto;
import ru.nikituz.rest.models.Document;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentDto toDto(Document document);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "person", ignore = true)
    Document toModel(DocumentDto documentDto);
}
