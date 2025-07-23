package com.lgn;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;

import javax.xml.transform.Source;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Component;
import org.springframework.ws.WebServiceMessage;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.xml.transform.StringSource;

@Component
public class SoapHelper {

  private final SaajSoapMessageFactory messageFactory;
  private final Jaxb2Marshaller marshaller;

  public SoapHelper(SaajSoapMessageFactory messageFactory,
                    Jaxb2Marshaller marshaller) {
    this.messageFactory = messageFactory;
    this.marshaller      = marshaller;
  }

  public <T> T unmarshalPayload(String soapXml, Class<T> payloadClass) throws Exception {
    // 1. Wrap raw XML in Spring-WS message
    ByteArrayInputStream in = new ByteArrayInputStream(
        soapXml.getBytes(StandardCharsets.UTF_8));
    WebServiceMessage wsMsg = messageFactory.createWebServiceMessage(in);

    // 2. Extract just the payload Source
    Source payloadSource = wsMsg.getPayloadSource();

    // 3. Unmarshal into your request POJO
    @SuppressWarnings("unchecked")
    T req = (T) marshaller.unmarshal(payloadSource);
    return req;
  }
  
  /**
   * Marshal a JAXB‐annotated POJO into a full SOAP XML envelope.
   */
  public <T> String marshalPayload(T payload) throws Exception {
    ByteArrayOutputStream out = new ByteArrayOutputStream();

    // 1. Create a blank SOAPMessage
    WebServiceMessage wsMsg = messageFactory.createWebServiceMessage();

    // 2. Marshal your POJO into the SOAP Body
    //    If your class lacks @XmlRootElement, wrap in JAXBElement with the proper QName:
    //    QName q = new QName("http://example.com/soap/model","MyRequest");
    //    JAXBElement<T> wrapper = new JAXBElement<>(q, (Class<T>)payload.getClass(), payload);
    //    marshaller.marshal(wrapper, wsMsg.getPayloadResult());
    marshaller.marshal(payload, wsMsg.getPayloadResult());

    // 3. Write out the full SOAP envelope
    wsMsg.writeTo(out);

    // 4. Return XML string
    return out.toString(StandardCharsets.UTF_8.name());
  }
  
}