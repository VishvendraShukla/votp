package com.votp.utils.response;

import org.springframework.http.ResponseEntity;

public interface BuilderContract<T> {

  ResponseEntity<T> build();

}
