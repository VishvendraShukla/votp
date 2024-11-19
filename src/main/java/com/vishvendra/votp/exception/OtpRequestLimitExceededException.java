package com.vishvendra.votp.exception;

public class OtpRequestLimitExceededException extends OtpException {

  public OtpRequestLimitExceededException(String identifier) {
    super(String.format("Otp request limit exceeded for identifier: %s", identifier));
  }
}
