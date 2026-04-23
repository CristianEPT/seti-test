package com.cristian.test.setitest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {

  @Bean
  public RestClient soapRestClient(@Value("${acme.soap.url}") String soapUrl) {
    return RestClient.builder()
        .baseUrl(soapUrl)
        .defaultHeader(HttpHeaders.CONTENT_TYPE, "text/xml;charset=UTF-8")
        .defaultHeader("SOAPAction", "")
        .build();
  }
}
