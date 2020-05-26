package com.ymmihw.spring.webflux.global;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorsOnGlobalConfigLiveTest {
  @LocalServerPort
  private int port;

  private static final String BASE_URL = "http://localhost:";
  private static final String BASE_REGULAR_URL = "/cors-on-global-config";
  private static final String BASE_EXTRA_CORS_CONFIG_URL = "/cors-on-global-config-and-more";
  private static final String BASE_FUNCTIONALS_URL = "/global-config-on-functional";
  private static final String CORS_DEFAULT_ORIGIN = "http://allowed-origin.com";

  private WebTestClient client;


  @BeforeEach
  public void setup() {
    client = WebTestClient.bindToServer().baseUrl(BASE_URL + port)
        .defaultHeader("Origin", CORS_DEFAULT_ORIGIN).build();
  }

  @Test
  public void whenRequestingRegularEndpoint_thenObtainResponseWithCorsHeaders() {
    ResponseSpec response = client.put().uri(BASE_REGULAR_URL + "/regular-put-endpoint").exchange();

    response.expectHeader().valueEquals("Access-Control-Allow-Origin", CORS_DEFAULT_ORIGIN);
  }

  @Test
  public void whenRequestingRegularDeleteEndpoint_thenObtainForbiddenResponse() {
    ResponseSpec response =
        client.delete().uri(BASE_REGULAR_URL + "/regular-delete-endpoint").exchange();

    response.expectStatus().isForbidden();
  }

  @Test
  public void whenPreflightAllowedDeleteEndpoint_thenObtainResponseWithCorsHeaders() {
    ResponseSpec response = client.options()
        .uri(BASE_EXTRA_CORS_CONFIG_URL + "/further-mixed-config-endpoint")
        .header("Access-Control-Request-Method", "DELETE").header("Access-Control-Request-Headers",
            "Baeldung-Not-Allowed, Baeldung-Allowed, Baeldung-Other-Allowed")
        .exchange();

    response.expectHeader().valueEquals("Access-Control-Allow-Origin", CORS_DEFAULT_ORIGIN);
    response.expectHeader().valueEquals("Access-Control-Allow-Methods", "PUT,DELETE");
    response.expectHeader().valueEquals("Access-Control-Allow-Headers",
        "Baeldung-Allowed, Baeldung-Other-Allowed");
    response.expectHeader().exists("Access-Control-Max-Age");
  }

  @Test
  public void whenRequestAllowedDeleteEndpoint_thenObtainResponseWithCorsHeaders() {
    ResponseSpec response = client.delete()
        .uri(BASE_EXTRA_CORS_CONFIG_URL + "/further-mixed-config-endpoint").exchange();

    response.expectStatus().isOk();
  }

  @Test
  public void whenPreflightFunctionalEndpoint_thenObtain404Response() {
    ResponseSpec response =
        client.options().uri(BASE_FUNCTIONALS_URL + "/cors-disabled-functional-endpoint")
            .header("Access-Control-Request-Method", "GET").exchange();

    response.expectStatus().isNotFound();
  }

  @Test
  public void whenRequestFunctionalEndpoint_thenObtainResponseWithCorsHeaders() {
    ResponseSpec response =
        client.put().uri(BASE_FUNCTIONALS_URL + "/cors-disabled-functional-endpoint").exchange();

    response.expectHeader().valueEquals("Access-Control-Allow-Origin", CORS_DEFAULT_ORIGIN);
  }
}
