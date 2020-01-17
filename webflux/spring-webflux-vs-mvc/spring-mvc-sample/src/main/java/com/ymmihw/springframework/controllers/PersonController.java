package com.ymmihw.springframework.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.ymmihw.springframework.models.Person;
import com.ymmihw.springframework.services.PersonRegistrationService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class PersonController {
  private final PersonRegistrationService personRegistrationService;

  @PostMapping("/persons")
  @ResponseStatus(HttpStatus.CREATED)
  public Person Person(@RequestBody Person person) {
    return personRegistrationService.addPerson(person);
  }
}
