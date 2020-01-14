package com.ymmihw.spring.webflux.errors.routers;


import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import com.ymmihw.spring.webflux.errors.handlers.Handler3;

@Component
public class Router3 {

  @Bean
  public RouterFunction<ServerResponse> routeRequest3(Handler3 handler) {
    return RouterFunctions.route(
        RequestPredicates.GET("/api/endpoint3").and(RequestPredicates.accept(MediaType.TEXT_PLAIN)),
        handler::handleRequest3);
  }

}
