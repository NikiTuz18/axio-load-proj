package ru.nikituz.rest.configurations;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import ru.nikituz.rest.client.ConvertedXmlClient;
import ru.nikituz.rest.configurations.properties.SoapProperties;

@Configuration
@RequiredArgsConstructor
public class XmlConverterSoapClientConfiguration {

    private final SoapProperties soapProperties;

    @Bean
    public ConvertedXmlClient convertedXmlClient(Jaxb2Marshaller marshaller){
        ConvertedXmlClient client = new ConvertedXmlClient();
        client.setDefaultUri(soapProperties.getUri());
        client.setMarshaller(marshaller);
        client.setUnmarshaller(marshaller);
        return client;
    }

    @Bean
    public Jaxb2Marshaller marshaller(){
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setContextPath("ru.nikituz.wsdl");
        return marshaller;
    }
}