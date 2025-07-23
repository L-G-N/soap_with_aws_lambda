package com.lgn;

import java.io.StringReader;

import javax.xml.transform.Source;

import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.xml.transform.StringSource;

import com.lgn.soap.GetSoapRequest;
import com.lgn.soap.GetSoapResponse;

import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Unmarshaller;

public class StringTest {

	public static void main(String[] args) {
		
		String a = "<soapenv:Envelope xmlns:soapenv=\\\"http://schemas.xmlsoap.org/soap/envelope/\\\" xmlns:soap=\\\"http://soap.lgn.com/\\\">\\r\\n   <soapenv:Header/>\\r\\n   <soapenv:Body>\\r\\n      <soap:getSoapRequest>\\r\\n  <soap:input>KLHJLKJLJJLJ</soap:input>\\r\\n      </soap:getSoapRequest>\\r\\n   </soapenv:Body>\\r\\n</soapenv:Envelope>";
		
		System.out.println(a.substring(a.indexOf("<soapenv:Body>")+"<soapenv:Body>".length(), a.lastIndexOf("</soapenv:Body>")));
		
		StringReader reader = new StringReader(a.substring(a.indexOf("<soapenv:Body>")+"<soapenv:Body>".length(), a.lastIndexOf("</soapenv:Body>")));
		System.out.println("********** Request : "+reader);
//		JAXBContext jaxbContext;
		try {
//			jaxbContext = JAXBContext.newInstance(GetSoapRequest.class, GetSoapResponse.class);
//			Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
//			GetSoapRequest request = (GetSoapRequest) unmarshaller.unmarshal(reader);
			
			Jaxb2Marshaller jaxb2 = new Jaxb2Marshaller();
			jaxb2.setContextPath("com.lgn.soap");
			StringSource source = new StringSource(reader.toString());
			GetSoapRequest getSoapRequest= (GetSoapRequest)jaxb2.unmarshal(source);
			System.out.println(getSoapRequest.getInput());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
