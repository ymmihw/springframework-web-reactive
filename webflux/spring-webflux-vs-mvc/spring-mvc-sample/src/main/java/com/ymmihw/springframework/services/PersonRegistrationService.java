package com.ymmihw.springframework.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.ymmihw.springframework.models.Person;

@Service
public class PersonRegistrationService {
  private final RestTemplate restTemplate;

  public PersonRegistrationService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate = restTemplateBuilder.build();
  }

  @Value("${registration.service}")
  private String registrationServiceEndpoint;

  public Person addPerson(Person person) {
    ResponseEntity<Person> postForEntity = restTemplate.postForEntity(
        registrationServiceEndpoint + "/register", new HttpEntity<>(person), Person.class);
    return postForEntity.getBody() == null ? person : postForEntity.getBody();
  }
}
