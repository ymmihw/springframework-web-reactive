package com.ymmihw.spring.webflux;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(classes = SpringSecurity5Application.class)
public class SecurityIntegrationTest {

  @Autowired
  ApplicationContext context;

  private WebTestClient rest;

  @BeforeEach
  public void setup() {
    this.rest = WebTestClient.bindToApplicationContext(this.context).configureClient().build();
  }

  @Test
  public void whenNoCredentials_thenRedirectToLogin() {
    this.rest.get().uri("/").exchange().expectStatus().is3xxRedirection();
  }

  @Test
  @Disabled
  @WithMockUser
  public void whenHasCredentials_thenSeesGreeting() {
    this.rest.get().uri("/").exchange().expectStatus().isOk().expectBody(String.class)
        .isEqualTo("Hello, user");
  }
}
