package com.votp.utils.response;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class BuilderContract<R, B> {

  public HttpStatus HTTP_STATUS = HttpStatus.OK;

  abstract public R build();

  @SuppressWarnings("unchecked")
  public B withHttpStatus(HttpStatus status) {
    HTTP_STATUS = status;
    return (B) this;
  }
}
