package com.ymmihw.springframework.controllers;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import com.ymmihw.springframework.models.Person;

@WebFluxTest
public class RegistrationControllerTest {
  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void should_register_a_person() {
    Person person = new Person("firstName", "secondName");

    webTestClient.post().uri("/register").contentType(APPLICATION_JSON).accept(APPLICATION_JSON)
        .body(BodyInserters.fromValue(person)).exchange().expectStatus().isCreated().expectBody()
        .jsonPath("firstName").isEqualTo(person.getFirstName()).jsonPath("lastName")
        .isEqualTo(person.getLastName()).jsonPath("id").isNotEmpty();
  }
}
