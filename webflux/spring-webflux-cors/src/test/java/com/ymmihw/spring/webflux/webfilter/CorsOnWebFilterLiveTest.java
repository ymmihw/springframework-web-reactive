package com.ymmihw.spring.webflux.webfilter;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CorsOnWebFilterLiveTest {
  @LocalServerPort
  private int port;

  private static final String BASE_URL = "http://localhost:";
  private static final String BASE_REGULAR_URL = "/web-filter-on-annotated";
  private static final String BASE_EXTRA_CORS_CONFIG_URL = "/web-filter-and-more-on-annotated";
  private static final String BASE_FUNCTIONALS_URL = "/web-filter-on-functional";
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
  public void whenPreflightDeleteEndpointWithExtraConfigs_thenObtainForbiddenResponse() {
    ResponseSpec response =
        client.options().uri(BASE_EXTRA_CORS_CONFIG_URL + "/further-mixed-config-endpoint")
            .header("Access-Control-Request-Method", "DELETE").exchange();

    response.expectStatus().isForbidden();
  }

  @Test
  public void whenRequestDeleteEndpointWithExtraConfigs_thenObtainForbiddenResponse() {
    ResponseSpec response = client.delete()
        .uri(BASE_EXTRA_CORS_CONFIG_URL + "/further-mixed-config-endpoint").exchange();

    response.expectStatus().isForbidden();
  }

  @Test
  public void whenPreflightFunctionalEndpoint_thenObtain404Response() {
    ResponseSpec response = client.options().uri(BASE_FUNCTIONALS_URL + "/functional-endpoint")
        .header("Access-Control-Request-Method", "PUT").exchange();

    response.expectHeader().valueEquals("Access-Control-Allow-Origin", CORS_DEFAULT_ORIGIN);
    response.expectHeader().valueEquals("Access-Control-Allow-Methods", "PUT");
    response.expectHeader().valueEquals("Access-Control-Max-Age", "8000");
  }

  @Test
  public void whenRequestFunctionalEndpoint_thenObtainResponseWithCorsHeaders() {
    ResponseSpec response =
        client.put().uri(BASE_FUNCTIONALS_URL + "/functional-endpoint").exchange();

    response.expectHeader().valueEquals("Access-Control-Allow-Origin", CORS_DEFAULT_ORIGIN);
  }
}
