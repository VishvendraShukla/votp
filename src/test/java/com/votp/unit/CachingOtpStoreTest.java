package com.votp.unit;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.votp.config.PlatformStarterConfiguration;
import com.votp.exception.OtpRequestLimitExceededException;
import com.votp.exception.OtpValidationLimitExceededException;
import com.votp.service.OtpService;
import jakarta.annotation.Resource;
import java.util.UUID;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = PlatformStarterConfiguration.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CachingOtpStoreTest {

  private static String generatedOtp;
  private static String identifier;
  private final Logger logger = LoggerFactory.getLogger(CachingOtpStoreTest.class);
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
