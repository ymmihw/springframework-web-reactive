package com.ymmihw.springframework.controllers;

import static org.springframework.http.HttpStatus.CREATED;
import java.time.Duration;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.ymmihw.springframework.models.Person;
import reactor.core.publisher.Mono;

@RestController
public class RegistrationController {
  @PostMapping("/register")
  @ResponseStatus(CREATED)
  public Mono<Person> register(@RequestBody Mono<Person> person) {
    return person.delayElement(Duration.ofMillis(200)).map(it -> it.copy(UUID.randomUUID()));
  }
}
