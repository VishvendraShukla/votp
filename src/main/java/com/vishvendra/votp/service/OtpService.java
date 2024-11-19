package com.vishvendra.votp.service;

import com.vishvendra.votp.exception.OtpRequestLimitExceededException;
import com.vishvendra.votp.exception.OtpValidationLimitExceededException;

public interface OtpService {

  String generateOtp(final String identifier) throws OtpRequestLimitExceededException;

  Boolean validateOtp(final String identifier, final String otp)
      throws OtpValidationLimitExceededException;

}
