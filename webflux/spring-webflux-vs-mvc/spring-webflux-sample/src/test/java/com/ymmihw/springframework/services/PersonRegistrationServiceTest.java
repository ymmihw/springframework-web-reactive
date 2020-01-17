package com.ymmihw.springframework.services;

import static com.github.tomakehurst.wiremock.client.WireMock.aResponse;
import static com.github.tomakehurst.wiremock.client.WireMock.matching;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.post;
import static com.github.tomakehurst.wiremock.client.WireMock.postRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.stubFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlMatching;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static java.util.Objects.isNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.ymmihw.springframework.models.Person;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PersonRegistrationServiceTest {
  @Autowired
  private PersonRegistrationService personRegistrationService;

  @Rule
  public WireMockRule wireMockRule = new WireMockRule(8090);

  @Test
  public void should_call_registrationService() {
    Person person = new Person("firstName", "lastName");
    stubFor(post("/register")
        .willReturn(aResponse().withHeader("Content-Type", APPLICATION_JSON_VALUE).withBody(
            "{\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"id\":\"ee0495d7-348f-44fc-968c-1d03104c0456\"}")));

    Mono<Person> mono = personRegistrationService.addPerson(person);
    StepVerifier.create(mono)
        .expectNextMatches(it -> it.getFirstName().equals(person.getFirstName())
            && it.getLastName().equals(person.getLastName()) && !isNull(it.getId()))
        .verifyComplete();

    verify(postRequestedFor(urlMatching("/register")).withRequestBody(matchingJsonPath("firstName"))
        .withRequestBody(matchingJsonPath("lastName"))
        .withHeader("Content-Type", matching(APPLICATION_JSON_VALUE)));
  }
}

