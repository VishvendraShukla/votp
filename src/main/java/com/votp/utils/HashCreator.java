package com.votp.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
@Profile("redis")
public class HashCreator {

  private final PasswordEncoder passwordEncoder;

  public String encode(final String otp) {
    return passwordEncoder.encode(otp);
  }

  public boolean match(String otp, String otpHash) {
    return passwordEncoder.matches(otp, otpHash);
  }

}
