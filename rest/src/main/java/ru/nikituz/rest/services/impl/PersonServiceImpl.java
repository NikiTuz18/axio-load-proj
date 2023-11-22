package ru.nikituz.rest.services.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.nikituz.rest.client.ConvertedXmlClient;
import ru.nikituz.rest.dto.PersonDto;
import ru.nikituz.rest.models.Person;
import ru.nikituz.rest.repositories.PersonRepository;
import ru.nikituz.rest.services.PersonService;
import ru.nikituz.rest.utils.exceptions.PersonCreateException;
import ru.nikituz.rest.utils.mappers.PersonMapper;
import ru.nikituz.rest.utils.response.ErrorPersonResponseTransfer;
import ru.nikituz.wsdl.GetConvertedXmlResponse;

import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
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
    public ResponseEntity<String> ResponseFromPost(PersonDto personDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors()){
            StringBuilder errorMessage = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                errorMessage.append(error.getField())
                        .append(" - ")
                        .append(error.getDefaultMessage())
                        .append(";\n");
            }

            throw new PersonCreateException(errorMessage.toString());
        }

        create(personDto);

        String xmlText = sendToSoap(personDto);

        return new ResponseEntity<>(xmlText, HttpStatus.OK);
    }

    @Override
    @Transactional
    public String sendToSoap(PersonDto personDto){
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

    @ExceptionHandler
    private ResponseEntity<ErrorPersonResponseTransfer> handlePersonException(PersonCreateException exception){
        return new ResponseEntity<>(
                new ErrorPersonResponseTransfer(exception.getMessage()),
                HttpStatus.BAD_REQUEST
        );
    }

    private String parseToXml(PersonDto personDto) throws JsonProcessingException {
        XmlMapper xmlMapper = new XmlMapper();
        xmlMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        xmlMapper.enable(SerializationFeature.INDENT_OUTPUT);
        return xmlMapper.writeValueAsString(personDto);
    }
}
