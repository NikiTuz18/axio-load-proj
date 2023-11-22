package ru.nikituz.soap.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;

@Service
@Slf4j
public class XmlConverterService {
    public String convert(String xml) {
        log.info("Начало процесса конвертации..");
        TransformerFactory transformerFactory = TransformerFactory.newInstance();

        try (InputStream converterScheme = getClass().getClassLoader().getResourceAsStream("static/converter.xsl");){
            Source xslSource = new StreamSource(converterScheme);

            Transformer transformer = transformerFactory.newTransformer(xslSource);
            transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            Source xmlSource = new StreamSource(new StringReader(xml));
            Writer resultWriter = new StringWriter();
            transformer.transform(xmlSource, new StreamResult(resultWriter));
            log.info("Конвертация прошла успешно!");
            return resultWriter.toString();
        } catch (IOException | TransformerException e) {
            log.error("Произошла ошибка конвертации: {}", e.getMessage() , e);
            throw new RuntimeException(e);
        }
    }
}
