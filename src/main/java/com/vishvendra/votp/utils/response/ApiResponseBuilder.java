package com.vishvendra.votp.utils.response;

import com.vishvendra.votp.utils.response.health.HealthApiResponseBuilder;
import com.vishvendra.votp.utils.response.successotp.SuccessOtpApiResponseBuilder;
import com.vishvendra.votp.utils.response.validation.ValidationApiResponseBuilder;
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

  public static OtpLimitExceededResponseBuilder getOtpLimitExceededResponseBuilder(
      MessageSource messageSource) {
    return new OtpLimitExceededResponseBuilder(messageSource);
  }
}
