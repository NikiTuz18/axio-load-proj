package ru.nikituz.rest.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.nikituz.rest.client.ConvertedXmlClient;
import ru.nikituz.rest.dto.PersonDto;
import ru.nikituz.rest.models.Person;
import ru.nikituz.rest.repositories.PersonRepository;
import ru.nikituz.rest.services.PersonService;
import ru.nikituz.rest.utils.exceptions.PersonCreateException;
import ru.nikituz.rest.utils.mappers.PersonMapper;
import ru.nikituz.wsdl.GetConvertedXmlResponse;

import java.io.StringReader;
import java.text.SimpleDateFormat;

@Service
@RequiredArgsConstructor
@Slf4j
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;
    private final ConvertedXmlClient convertedXmlClient;

    @Override
    @Transactional
    public void create(PersonDto personDto) {
        Person person = personMapper.toModel(personDto);
        personRepository.save(person);
    }


    @Override
    @Transactional
    public String sendToSoap(PersonDto personDto) throws PersonCreateException{
        try {
            String xmlText = parseToXml(personDto);
            GetConvertedXmlResponse response = convertedXmlClient.getConvertedXml(xmlText);

            JAXBContext jaxbContext = JAXBContext.newInstance(PersonDto.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            PersonDto convertedPersonDto = (PersonDto) jaxbUnmarshaller.unmarshal(new StringReader(response.getConvertedXmlText()));
            create(convertedPersonDto);

            return response.getConvertedXmlText();
        } catch (JsonProcessingException | JAXBException e) {
            throw new PersonCreateException(e.getMessage());
        }
    }

    private String parseToXml(PersonDto personDto) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper.writeValueAsString(personDto);
    }
}
