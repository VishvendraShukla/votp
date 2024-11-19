package com.vishvendra.votp.exception;

public class OtpValidationLimitExceededException extends OtpException {

  public OtpValidationLimitExceededException(String identifier) {
    super(String.format("Otp validation limit exceeded for identifier: %s", identifier));
  }
}
