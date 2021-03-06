package com.ymmihw.spring.webflux;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StaticContentDefaultLocationIntegrationTest {

  @Autowired
  private WebTestClient client;

  @Test
  public void whenRequestingHtmlFileFromDefaultLocation_thenReturnThisFileAndTextHtmlContentType()
      throws Exception {
    client.get().uri("/").exchange().expectStatus().isOk().expectHeader()
        .valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE);
  }

  @Test
  public void whenRequestingPngImageFromImgLocation_thenReturnThisFileAndImagePngContentType()
      throws Exception {
    client.get().uri("/img/example-image.png").exchange().expectStatus().isOk().expectHeader()
        .valueEquals(HttpHeaders.CONTENT_TYPE, MediaType.IMAGE_PNG_VALUE);
  }

  @Test
  public void whenRequestingMissingStaticResource_thenReturnNotFoundStatus() throws Exception {
    client.get().uri("/unknown.png").exchange().expectStatus().isNotFound();
  }

}
