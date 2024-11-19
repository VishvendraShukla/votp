package com.vishvendra.votp.service.throttle;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;

public class OtpThrottleService {


  private Cache<String, Integer> otpRequestCache;

  private Cache<String, Integer> otpValidationCache;

  @Value("${otp.request-limit:3}")
  private int maxOtpRequests;

  @Value("${otp.request-reset-in-millis:300000}")
  private int requestResetMinutes;

  @Value("${otp.max-validation-attempts:5}")
  private int maxValidationAttempts;

  @PostConstruct
  public void init() {
    this.otpRequestCache = CacheBuilder.newBuilder()
        .expireAfterWrite(requestResetMinutes, TimeUnit.MILLISECONDS).build();

    this.otpValidationCache = CacheBuilder.newBuilder()
        .expireAfterWrite(maxValidationAttempts, TimeUnit.MINUTES).build();
  }

  public Boolean canGenerateOtp(final String identifier) {
    Integer attempts = otpRequestCache.getIfPresent(identifier);

    if (Objects.isNull(attempts)) {
      otpRequestCache.put(identifier, 1);
      return true;
    }

    if (attempts < maxOtpRequests) {
      otpRequestCache.put(identifier, attempts + 1);
      return true;
    }

    return false;
  }

  public Boolean canValidateOtp(final String identifier, final String otp) {
    String key = identifier + ":" + otp;
    Integer attempts = otpValidationCache.getIfPresent(key);

    if (Objects.isNull(attempts)) {
      otpValidationCache.put(key, 1);
      return true;
    }

    if (attempts < maxValidationAttempts) {
      otpValidationCache.put(key, attempts + 1);
      return true;
    }

    return false;
  }

  public void clearValidationAttempts(final String identifier, final String otp) {
    String key = identifier + ":" + otp;
    otpValidationCache.invalidate(key);
  }

}
