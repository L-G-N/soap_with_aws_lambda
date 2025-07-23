package com.lgn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;

@SpringBootApplication
public class SoapWithSpringBootApplication {

	public static void main(String[] args) {
		SpringApplication.run(SoapWithSpringBootApplication.class, args);
	}

    @Bean
    public Jaxb2Marshaller jaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // package where your generated/request classes live
        marshaller.setContextPath("com.lgn.soap");
        return marshaller;
    }
    

	@Bean
	public ObjectMapper jsonMapper() {
		ObjectMapper mapper = new ObjectMapper();
		// If your POJOs use JAXB annotations (@XmlElement, @XmlRootElement…)
		mapper.registerModule(new JaxbAnnotationModule());
		// pretty‐print for readability
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		return mapper;
	}
    
}
