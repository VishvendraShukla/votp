package com.votp.service.store;

import java.util.Random;

public class BaseOtpMechanism {

  private final int otpLength;

  public BaseOtpMechanism(int otpLength) {
    this.otpLength = otpLength;
  }

  protected String generateRandomOtp() {
    StringBuilder otp = new StringBuilder();
    Random random = new Random();
    for (int i = 0; i < otpLength; i++) {
      otp.append(random.nextInt(10));
    }
    return otp.toString();
  }

}
