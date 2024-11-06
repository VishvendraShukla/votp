package com.votp.service.store;

import com.votp.entity.OtpCache;
import com.votp.repository.OtpCacheRepository;
import com.votp.utils.HashCreator;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;

@Profile("redis")
public class RedisOtpStore extends BaseOtpMechanism implements OtpStore {

  @Autowired
  private OtpCacheRepository otpCacheRepository;

  @Autowired
  private HashCreator hashCreator;

  @Value("${otp.expiry-duration-in-millis:300000}")
  private Long otpExpiryDuration;

  public RedisOtpStore(@Value("${otp.length:5}") int otpLength) {
    super(otpLength);
  }

  @Override
  public String generateOtp(String identifier) {
    String randomOtp = generateRandomOtp();
    OtpCache newOtpCache = new OtpCache(identifier, this.hashCreator.encode(randomOtp),
        otpExpiryDuration);
    this.otpCacheRepository.save(newOtpCache);
    return randomOtp;
  }

  @Override
  public Boolean validateOtp(String identifier, String otp) {
    OtpCache cachedOtp = this.otpCacheRepository.findByIdentifier(identifier);
    if (Objects.nonNull(cachedOtp) && this.hashCreator.match(otp, cachedOtp.getOtpHash())) {
      this.otpCacheRepository.delete(cachedOtp);
      return Boolean.TRUE;
    }
    return Boolean.FALSE;
  }
}
