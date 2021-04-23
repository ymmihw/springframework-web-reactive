package com.ymmihw.spring.webflux;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;
import com.ymmihw.spring.Spring5ReactiveApplication;
import com.ymmihw.spring.webflux.controller.PathPatternController;

@SpringBootTest(classes = Spring5ReactiveApplication.class)
@WithMockUser
public class PathPatternsUsingHandlerMethodIntegrationTest {

  private static WebTestClient client;

  @BeforeAll
  public static void setUp() {
    client = WebTestClient.bindToController(new PathPatternController()).build();
  }

  @Test
  public void givenHandlerMethod_whenMultipleURIVariablePattern_then200() {

    client.get().uri("/spring5/ab/cd").exchange().expectStatus().is2xxSuccessful().expectBody()
        .equals("/ab/cd");
  }

  @Test
  public void givenHandlerMethod_whenURLWithWildcardTakingZeroOrMoreChar_then200() {

    client.get().uri("/spring5/userid").exchange().expectStatus().is2xxSuccessful().expectBody()
        .equals("/spring5/*id");
  }

  @Test
  public void givenHandlerMethod_whenURLWithWildcardTakingExactlyOneChar_then200() {

    client.get().uri("/string5").exchange().expectStatus().is2xxSuccessful().expectBody()
        .equals("/s?ring5");
  }

  @Test
  public void givenHandlerMethod_whenURLWithWildcardTakingZeroOrMorePathSegments_then200() {

    client.get().uri("/resources/baeldung").exchange().expectStatus().is2xxSuccessful().expectBody()
        .equals("/resources/**");
  }

  @Test
  public void givenHandlerMethod_whenURLWithRegexInPathVariable_thenExpectedOutput() {

    client.get().uri("/abc").exchange().expectStatus().is2xxSuccessful().expectBody().equals("abc");

    client.get().uri("/123").exchange().expectStatus().is4xxClientError();
  }

  @Test
  public void givenHandlerMethod_whenURLWithMultiplePathVariablesInSameSegment_then200() {

    client.get().uri("/baeldung_tutorial").exchange().expectStatus().is2xxSuccessful().expectBody()
        .equals("Two variables are var1=baeldung and var2=tutorial");
  }

}
