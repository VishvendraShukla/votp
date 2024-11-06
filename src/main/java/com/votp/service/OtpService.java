package com.votp.service;

import com.votp.exception.OtpRequestLimitExceededException;
import com.votp.exception.OtpValidationLimitExceededException;

public interface OtpService {

  String generateOtp(final String identifier) throws OtpRequestLimitExceededException;

  Boolean validateOtp(final String identifier, final String otp)
      throws OtpValidationLimitExceededException;

}
