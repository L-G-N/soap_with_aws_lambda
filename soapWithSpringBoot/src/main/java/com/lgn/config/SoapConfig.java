package com.lgn.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.soap.saaj.SaajSoapMessageFactory;
import org.springframework.ws.soap.SoapVersion;

@Configuration
public class SoapConfig {

  @Bean
  public SaajSoapMessageFactory messageFactory() {
    SaajSoapMessageFactory factory = new SaajSoapMessageFactory();
    factory.setSoapVersion(SoapVersion.SOAP_11);       // or SOAP_12
    factory.afterPropertiesSet();                      // init the SAAJ underneath
    return factory;
  }
}