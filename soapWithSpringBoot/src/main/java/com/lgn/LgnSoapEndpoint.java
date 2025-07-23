package com.lgn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgn.soap.GetSoapRequest;
import com.lgn.soap.GetSoapResponse;


@Endpoint
public class LgnSoapEndpoint {
	
	@Autowired
	private SoapHelper soapHelper;
	
	@Autowired
	private ObjectMapper jsonmapper;
	
	@PayloadRoot(namespace = "http://soap.lgn.com/", localPart = "getSoapRequest")
	@ResponsePayload
	public GetSoapResponse getSoap(@RequestPayload GetSoapRequest getSoapRequest) {
		
		GetSoapRequest request;
		try {
			request = unmarshalSoapBody("<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:soap=\"http://soap.lgn.com/\">\\r\\n\r\n <soapenv:Header/>\\r\\n   <soapenv:Body>\\r\\n      <soap:getSoapRequest>\\r\\n         <soap:input>KLHJLKJLJJLJ</soap:input>\\r\\n      </soap:getSoapRequest>\\r\\n   </soapenv:Body>\\r\\n</soapenv:Envelope>");
			String json = jsonmapper.writeValueAsString(request);
			System.out.println(json);
			 // 1. JSON → POJO
			GetSoapRequest req = jsonmapper.readValue(json, GetSoapRequest.class);

		    // 2. POJO → SOAP XML
		    System.out.println(soapHelper.marshalPayload(req));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GetSoapResponse response = new GetSoapResponse();
		response.setOutput( getSoapRequest.getInput() + " -->> Hi from SOAP - LGN !!!!");
		return response;
	}
	
	
 // 2. Unmarshal the <soap:Body> payload into your POJO
    public GetSoapRequest unmarshalSoapBody(String soapXml) throws Exception {
    	return soapHelper.unmarshalPayload(soapXml, GetSoapRequest.class);
    }
	
    

}
