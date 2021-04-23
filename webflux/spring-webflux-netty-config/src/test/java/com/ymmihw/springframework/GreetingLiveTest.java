package com.ymmihw.springframework;

import javax.net.ssl.SSLException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.test.web.reactive.server.WebTestClient.ResponseSpec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.InsecureTrustManagerFactory;
import reactor.netty.http.client.HttpClient;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ContextConfiguration(classes = ServerConfigApplication.class)
@DirtiesContext
public class GreetingLiveTest {

  private static final String BASE_URL = "https://localhost:8443";

  private WebTestClient webTestClient;

  @BeforeEach
  public void setup() throws SSLException {
    webTestClient = WebTestClient.bindToServer(getConnector()).baseUrl(BASE_URL).build();
  }

  @Test
  public void shouldGreet() {
    final String name = "Baeldung";

    ResponseSpec response = webTestClient.get().uri("/greet/{name}", name).exchange();

    response.expectStatus().isOk().expectBody(String.class).isEqualTo("Greeting Baeldung");
  }

  private ReactorClientHttpConnector getConnector() throws SSLException {
    SslContext sslContext =
        SslContextBuilder.forClient().trustManager(InsecureTrustManagerFactory.INSTANCE).build();
    HttpClient httpClient = HttpClient.create().secure(t -> t.sslContext(sslContext));
    return new ReactorClientHttpConnector(httpClient);
  }
}
