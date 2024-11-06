package com.votp.service.store;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import jakarta.annotation.PostConstruct;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import org.springframework.beans.factory.annotation.Value;

public class CachingOtpStore extends BaseOtpMechanism implements OtpStore {


  private Cache<String, String> otpCache;

  @Value("${otp.expiry-duration-in-millis:300000}")
  private int otpExpiryDuration;

  public CachingOtpStore(@Value("${otp.length:5}") int otpLength) {
    super(otpLength);
  }

  @PostConstruct
  public void init() {
    this.otpCache = CacheBuilder.newBuilder()
        .initialCapacity(50)
        .expireAfterWrite(otpExpiryDuration, TimeUnit.MILLISECONDS)
        .build();
  }


  @Override
  public String generateOtp(String identifier) {
    String randomOtp = generateRandomOtp();
    this.otpCache.put(identifier, randomOtp);
    return randomOtp;
  }

  @Override
  public Boolean validateOtp(String identifier, String otp) {
    String cachedOtp = otpCache.getIfPresent(identifier);
    if (Objects.nonNull(cachedOtp) && cachedOtp.equals(otp)) {
      this.otpCache.invalidate(identifier);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }


}
