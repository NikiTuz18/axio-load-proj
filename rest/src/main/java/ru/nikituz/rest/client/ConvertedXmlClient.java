package ru.nikituz.rest.client;


import org.springframework.ws.client.core.support.WebServiceGatewaySupport;
import org.springframework.ws.soap.client.core.SoapActionCallback;
import ru.nikituz.wsdl.GetConvertedXmlRequest;
import ru.nikituz.wsdl.GetConvertedXmlResponse;

public class ConvertedXmlClient extends WebServiceGatewaySupport {
    public GetConvertedXmlResponse getConvertedXml(String xmlText){
        GetConvertedXmlRequest request = new GetConvertedXmlRequest();
        request.setSourceXmlText(xmlText);

        return (GetConvertedXmlResponse) getWebServiceTemplate()
                .marshalSendAndReceive("http://localhost:9090/soap/convert",
                        request,
                        new SoapActionCallback("https://nikituz.ru/soap/GetConvertedXmlRequest"));
    }
}
