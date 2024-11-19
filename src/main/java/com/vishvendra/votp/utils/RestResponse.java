package com.vishvendra.votp.utils;

import com.vishvendra.votp.models.Response;
import com.vishvendra.votp.models.Response.Payload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class RestResponse {


  public static ResponseEntity<Response> success(final String message) {
    return ResponseEntity.ok(Response.builder()
        .isError(false)
        .payload(Payload.builder().message(message).build())
        .build());
  }

  public static ResponseEntity<Response> success() {
    return ResponseEntity.ok(Response.builder().isError(false).build());
  }

  public static ResponseEntity<Response> error(final String message) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Response.builder()
            .isError(true)
            .payload(Payload.builder().errorMessage(message).build())
            .build());
  }

  public static ResponseEntity<Response> error() {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(Response.builder().isError(true).build());
  }

}
