package com.votp.exception;

public class RedisConnectionEstablishException extends RuntimeException {

  public RedisConnectionEstablishException(String message, Throwable cause) {
    super(message, cause);
  }
}
