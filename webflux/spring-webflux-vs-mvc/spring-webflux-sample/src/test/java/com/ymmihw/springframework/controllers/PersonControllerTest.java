package com.ymmihw.springframework.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.ymmihw.springframework.models.Person;
import com.ymmihw.springframework.services.PersonRegistrationService;
import reactor.core.publisher.Mono;

@RunWith(SpringRunner.class)
@WebFluxTest
public class PersonControllerTest {
  @MockBean
  private PersonRegistrationService personRegistrationService;

  @Autowired
  private WebTestClient webClient;

  @Test
  public void should_add_a_person() {
    Person person = new Person("firstName", "secondName");

    when(personRegistrationService.addPerson(any()))
        .thenReturn(Mono.just(person.copy(UUID.randomUUID())));
    webClient.post().uri("/persons").accept(APPLICATION_JSON).body(Mono.just(person), Person.class)
        .exchange().expectStatus().isCreated().expectBody().jsonPath("firstName")
        .isEqualTo(person.getFirstName()).jsonPath("lastName").isEqualTo(person.getLastName())
        .jsonPath("id").isNotEmpty();

  }
}
