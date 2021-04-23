package com.ymmihw.spring.webflux.filter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@WithMockUser
public class PlayerHandlerIntegrationTest {

  @Autowired
  private WebTestClient webTestClient;

  @Test
  public void whenPlayerNameIsBaeldung_thenWebFilterIsApplied() {
    EntityExchangeResult<String> result = webTestClient.get().uri("/players/baeldung").exchange()
        .expectStatus().isOk().expectBody(String.class).returnResult();

    assertEquals(result.getResponseBody(), "baeldung");
    assertEquals(result.getResponseHeaders().getFirst("web-filter"), "web-filter-test");
  }

  @Test
  public void whenPlayerNameIsTest_thenHandlerFilterFunctionIsApplied() {
    webTestClient.get().uri("/players/test").exchange().expectStatus().isForbidden();
  }

}
