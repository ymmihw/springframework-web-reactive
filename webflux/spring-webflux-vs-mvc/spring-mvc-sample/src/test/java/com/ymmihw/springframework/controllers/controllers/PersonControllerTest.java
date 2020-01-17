package com.ymmihw.springframework.controllers.controllers;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.UUID;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ymmihw.springframework.models.Person;
import com.ymmihw.springframework.services.PersonRegistrationService;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PersonControllerTest {
  @MockBean
  private PersonRegistrationService personRegistrationService;

  @Autowired
  private MockMvc mockMvc;
  @Autowired
  private ObjectMapper mapper;

  @Test
  public void add_a_Person() throws Exception {
    Person person = new Person("firstName", "lastName");
    when(personRegistrationService.addPerson(any())).thenReturn(person.copy(UUID.randomUUID()));

    MvcResult result = mockMvc
        .perform(post("/persons").content(mapper.writeValueAsString(person))
            .contentType(APPLICATION_JSON_VALUE).accept(APPLICATION_JSON_VALUE))
        .andExpect(status().isCreated()).andReturn();

    Person addedPerson = mapper.readValue(result.getResponse().getContentAsString(), Person.class);
    assertThat(addedPerson.getFirstName()).isEqualTo(person.getFirstName());
    assertThat(addedPerson.getLastName()).isEqualTo(person.getLastName());
    assertThat(addedPerson.getId()).isNotNull();
  }

}
