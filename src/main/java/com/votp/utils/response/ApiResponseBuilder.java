package com.votp.utils.response;

import com.votp.utils.response.health.HealthApiResponseBuilder;
import com.votp.utils.response.successotp.SuccessOtpApiResponseBuilder;
import com.votp.utils.response.validation.ValidationApiResponseBuilder;
import org.springframework.context.MessageSource;

public class ApiResponseBuilder {

  public static SuccessOtpApiResponseBuilder getSuccessOtpApiResponse(MessageSource messageSource) {
    return new SuccessOtpApiResponseBuilder(messageSource);
  }

  public static HealthApiResponseBuilder getHealthApiResponseBuilder(MessageSource messageSource) {
    return new HealthApiResponseBuilder(messageSource);
  }

  public static ValidationApiResponseBuilder getValidationApiResponseBuilder(
      MessageSource messageSource) {
    return new ValidationApiResponseBuilder(messageSource);
  }
}
