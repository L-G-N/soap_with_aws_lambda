package com.lgn;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ws.server.endpoint.mapping.PayloadRootQNameEndpointMapping;
import org.springframework.ws.server.endpoint.mapping.UriEndpointMapping;
import org.springframework.ws.soap.server.endpoint.mapping.SoapActionEndpointMapping;

@Configuration
public class EndpointMappingConfig {

  @Bean
  public SoapActionEndpointMapping soapActionEndpointMapping(ApplicationContext ctx) {
    SoapActionEndpointMapping m = new SoapActionEndpointMapping();
    m.setApplicationContext(ctx);
//    m.afterPropertiesSet();
    return m;
  }

  @Bean
  public UriEndpointMapping uriEndpointMapping(ApplicationContext ctx) {
    UriEndpointMapping m = new UriEndpointMapping();
    m.setApplicationContext(ctx);
//    m.afterPropertiesSet();
    return m;
  }

  @Bean
  public PayloadRootQNameEndpointMapping payloadRootQNameEndpointMapping(ApplicationContext ctx) {
    PayloadRootQNameEndpointMapping m = new PayloadRootQNameEndpointMapping();
    m.setApplicationContext(ctx);
//    m.afterPropertiesSet();
    return m;
  }
}