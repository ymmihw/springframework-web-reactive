package com.ymmihw.springframework.models;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Person {
  private UUID id;
  private String firstName;
  private String lastName;


  public Person(String firstName, String lastName) {
    super();
    this.firstName = firstName;
    this.lastName = lastName;
  }

  public Person copy(UUID uuid) {
    return new Person(uuid, firstName, lastName);
  }

}
