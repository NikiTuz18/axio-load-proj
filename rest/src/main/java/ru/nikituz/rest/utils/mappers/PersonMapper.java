package ru.nikituz.rest.utils.mappers;

import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.nikituz.rest.dto.PersonDto;
import ru.nikituz.rest.models.Person;

@Mapper(componentModel = "spring", uses = DocumentMapper.class, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface PersonMapper {

    PersonDto toDto(Person person);

    @Mapping(target = "id", ignore = true)
    Person toModel(PersonDto personDto);
}
