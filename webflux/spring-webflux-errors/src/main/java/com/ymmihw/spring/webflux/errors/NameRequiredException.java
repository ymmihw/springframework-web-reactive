package com.ymmihw.spring.webflux.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class NameRequiredException extends ResponseStatusException {
  private static final long serialVersionUID = 1L;

  public NameRequiredException(HttpStatus status, String message, Throwable e) {
    super(status, message, e);
  }
}
