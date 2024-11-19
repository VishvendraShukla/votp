package com.vishvendra.votp.service.store;

public interface OtpStore {

  String generateOtp(final String identifier);

  Boolean validateOtp(final String identifier, final String otp);
}
