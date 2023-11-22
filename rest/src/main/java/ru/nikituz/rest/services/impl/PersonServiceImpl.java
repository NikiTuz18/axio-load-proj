package ru.nikituz.rest.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
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
import java.util.List;

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
    public ResponseEntity<String> responseFromPost(PersonDto personDto, BindingResult bindingResult) throws PersonCreateException {
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";\n");
            }
            log.error("Ошибка в корректности данных JSON-модели.");
            throw new PersonCreateException(errorMessage.toString());
        }

        create(personDto);
        log.info("Данные успешно добавлены в БД (json)!");
        log.info("Обращение к SOAP-модулю..");
        String xmlText = sendToSoap(personDto);
        log.info("SOAP-модуль успешно завершил конвертацию и вернул ответ!");
        return new ResponseEntity<>(xmlText, HttpStatus.OK);
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
