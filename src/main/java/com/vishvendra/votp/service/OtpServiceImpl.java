package com.vishvendra.votp.service;

import com.vishvendra.votp.exception.OtpRequestLimitExceededException;
import com.vishvendra.votp.exception.OtpValidationLimitExceededException;
import com.vishvendra.votp.service.store.OtpStore;
import com.vishvendra.votp.service.throttle.OtpThrottleService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {


  @Resource(name = "primaryOtpStore")
  private final OtpStore otpStore;

  @Autowired
  private final OtpThrottleService otpThrottleService;

  @Override
  public String generateOtp(String identifier) throws OtpRequestLimitExceededException {
    if (otpThrottleService.canGenerateOtp(identifier)) {
      return otpStore.generateOtp(identifier);
    }
    throw new OtpRequestLimitExceededException(identifier);
  }

  @Override
  public Boolean validateOtp(String identifier, String otp)
      throws OtpValidationLimitExceededException {
    if (otpThrottleService.canValidateOtp(identifier, otp)) {
      return otpStore.validateOtp(identifier, otp);
    }
    throw new OtpValidationLimitExceededException(identifier);
  }
}
