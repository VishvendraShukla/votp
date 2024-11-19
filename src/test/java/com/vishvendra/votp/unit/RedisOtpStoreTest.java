package com.vishvendra.votp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.vishvendra.votp.config.HashingConfig;
import com.vishvendra.votp.config.PlatformStarterConfiguration;
import com.vishvendra.votp.config.RedisConfig;
import com.vishvendra.votp.exception.OtpRequestLimitExceededException;
import com.vishvendra.votp.exception.OtpValidationLimitExceededException;
import com.vishvendra.votp.service.OtpService;
import jakarta.annotation.Resource;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes =
    {PlatformStarterConfiguration.class,
        HashingConfig.class,
        RedisConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("redis")
public class RedisOtpStoreTest {

  private static String generatedOtp;
  private static String identifier;
  private final Logger logger = LoggerFactory.getLogger(RedisOtpStoreTest.class);
  @Resource(name = "otpService")
  protected OtpService otpService;

  @Test
  @Order(1)
  public void testGenerateOtpSuccess() throws OtpRequestLimitExceededException {
    identifier = UUID.randomUUID().toString();
    generatedOtp = otpService.generateOtp(identifier);
    logger.info("Otp generated: {}", generatedOtp);
    assertNotNull(generatedOtp, "Generated OTP should not be null.");
  }

  @Test
  @Order(2)
  public void testValidateOtpSuccess() throws OtpValidationLimitExceededException {
    Boolean validation = otpService.validateOtp(identifier, generatedOtp);
    logger.info("Otp validated to: {}", validation);
    assertEquals(true, validation, "OTP validation should be successful.");
  }

}
