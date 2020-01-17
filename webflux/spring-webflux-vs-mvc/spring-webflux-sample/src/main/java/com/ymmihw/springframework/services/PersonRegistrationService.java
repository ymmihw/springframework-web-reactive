package com.ymmihw.springframework.services;

import static org.springframework.http.HttpHeaders.ACCEPT;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import com.ymmihw.springframework.models.Person;
import reactor.core.publisher.Mono;

@Service
public class PersonRegistrationService {

  @Value("${registration.service}")
  private String registrationServiceEndpoint;

  private WebClient webClient;

  @PostConstruct
  public void postConstruct() {
    this.webClient = WebClient.builder().baseUrl(registrationServiceEndpoint).build();
  }

  public Mono<Person> addPerson(Person person) {
    return webClient.post().uri("/register").header(CONTENT_TYPE, APPLICATION_JSON_VALUE)
        .header(ACCEPT, APPLICATION_JSON_VALUE).body(fromValue(person)).exchange()
        .flatMap(it -> it.bodyToMono(Person.class));
  }

}
