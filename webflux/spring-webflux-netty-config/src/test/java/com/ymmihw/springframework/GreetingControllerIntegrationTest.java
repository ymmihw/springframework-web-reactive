package com.ymmihw.springframework;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

@WebFluxTest
public class GreetingControllerIntegrationTest {

  @Autowired
  private WebTestClient webClient;

  @MockBean
  private GreetingService greetingService;

  private final String name = "Baeldung";

  @BeforeEach
  public void setUp() {
    when(greetingService.greet(name)).thenReturn(Mono.just("Greeting Baeldung"));
  }

  @Test
  public void shouldGreet() {
    webClient.get().uri("/greet/{name}", name).exchange().expectStatus().isOk()
        .expectBody(String.class).isEqualTo("Greeting Baeldung");
  }
}
