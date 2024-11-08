package com.votp.exception;

public class RedisConnectionEstablishException extends AppException {

  public RedisConnectionEstablishException(String message, Throwable cause) {
    super(message, cause);
  }
}
