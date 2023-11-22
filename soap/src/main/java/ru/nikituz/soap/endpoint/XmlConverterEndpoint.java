package ru.nikituz.soap.endpoint;

import https.nikituz_ru.soap.GetConvertedXmlRequest;
import https.nikituz_ru.soap.GetConvertedXmlResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import ru.nikituz.soap.service.XmlConverterService;

import static ru.nikituz.soap.util.UriUtil.NAMESPACE_URI;

@Endpoint
@RequiredArgsConstructor
public class XmlConverterEndpoint {

    final XmlConverterService xmlConverterService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "getConvertedXmlRequest")
    @ResponsePayload
    public GetConvertedXmlResponse getConvertedXmlResponse(@RequestPayload GetConvertedXmlRequest getConvertedXmlRequest){
        GetConvertedXmlResponse getConvertedXmlResponse = new GetConvertedXmlResponse();
        getConvertedXmlResponse.setConvertedXmlText(xmlConverterService.convert(getConvertedXmlRequest.getSourceXmlText()));
        return getConvertedXmlResponse;
    }
}
