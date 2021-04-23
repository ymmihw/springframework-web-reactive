package com.ymmihw.springframework.controllers.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.ymmihw.springframework.controllers.WireMockExtension;
import com.ymmihw.springframework.models.Person;
import com.ymmihw.springframework.services.PersonRegistrationService;

@SpringBootTest
public class PersonRegistrationServiceTest {
  @Autowired
  private PersonRegistrationService personRegistrationService;

  @RegisterExtension
  WireMockExtension mockServer1 = new WireMockExtension(8090);

  @Test
  public void should_call_registrationService() {
    Person person = new Person("firstName", "lastName");
    stubFor(post("/register")
        .willReturn(aResponse().withHeader("Content-Type", APPLICATION_JSON_VALUE).withBody(
            "{\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"id\":\"ee0495d7-348f-44fc-968c-1d03104c0456\"}")));

    Person result = personRegistrationService.addPerson(person);

    assertThat(result.getFirstName()).isEqualTo(person.getFirstName());
    assertThat(result.getLastName()).isEqualTo(person.getLastName());
    assertThat(result.getId()).isNotNull();

    verify(postRequestedFor(urlMatching("/register")).withRequestBody(matchingJsonPath("firstName"))
        .withRequestBody(matchingJsonPath("lastName"))
        .withHeader("Content-Type", matching(APPLICATION_JSON_VALUE)));
  }

}
