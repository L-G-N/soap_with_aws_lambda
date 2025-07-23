package com.lgn;

import java.util.Map;

import org.springframework.context.ApplicationContext;

import com.amazonaws.serverless.exceptions.ContainerInitializationException;
import com.amazonaws.serverless.proxy.model.AwsProxyRequest;
import com.amazonaws.serverless.proxy.model.AwsProxyResponse;
import com.amazonaws.serverless.proxy.spring.SpringBootLambdaContainerHandler;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lgn.config.ApplicationContextProvider;
import com.lgn.soap.GetSoapRequest;


public class StreamLambdaHandler implements RequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {
    private static SpringBootLambdaContainerHandler<AwsProxyRequest, AwsProxyResponse> handler;
    static {
        try {
            handler = SpringBootLambdaContainerHandler.getAwsProxyHandler(SoapWithSpringBootApplication.class);
        } catch (ContainerInitializationException e) {
            // if we fail here. We re-throw the exception to force another cold start
            e.printStackTrace();
            throw new RuntimeException("Could not initialize Spring Boot application", e);
        }
    }

	@Override
	public APIGatewayProxyResponseEvent handleRequest(APIGatewayProxyRequestEvent event, Context context) {
		String resposneXml = null;
		try {
			// get the app context to retrive the beans
			ApplicationContext applicationContext = ApplicationContextProvider.getApplicationContext();
			SoapHelper soapHelper = applicationContext.getBean("soapHelper",SoapHelper.class);
			ObjectMapper objectMapper = applicationContext.getBean("jsonMapper", ObjectMapper.class);
			
			// 1. convert xml request to pojo
			GetSoapRequest getSoapRequest = soapHelper.unmarshalPayload(event.getBody(),GetSoapRequest.class);
			
			// 2. pojo to json string for rest api request
			String json = objectMapper.writeValueAsString(getSoapRequest);
			System.out.println(json);
			
			// 3. JSON → POJO
			GetSoapRequest req = objectMapper.readValue(json, GetSoapRequest.class);

		    // 4. POJO → SOAP XML
			resposneXml = soapHelper.marshalPayload(req);
		    System.out.println(resposneXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return new APIGatewayProxyResponseEvent().withStatusCode(200)
		.withHeaders(Map.of("Content-Type", "text/xml")).withBody(resposneXml);

	}
    
}