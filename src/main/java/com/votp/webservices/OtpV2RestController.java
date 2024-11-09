package com.votp.webservices;


import com.votp.exception.OtpRequestLimitExceededException;
import com.votp.exception.OtpValidationLimitExceededException;
import com.votp.models.Notification;
import com.votp.models.OtpNotify;
import com.votp.models.Response;
import com.votp.notification.NotificationMessageBuilder;
import com.votp.notification.NotificationMessageBuilder.NotificationType;
import com.votp.notification.NotifierRegistry;
import com.votp.service.OtpService;
import com.votp.utils.response.ApiResponseBuilder;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/otp")
@RequiredArgsConstructor
public class OtpV2RestController {

  private final OtpService otpService;
  private final MessageSource messageSource;

  @PostMapping(value = "create")
  public ResponseEntity<Response> createAndStoreOtp(@RequestBody final OtpNotify otp)
      throws OtpRequestLimitExceededException {
    try {
      String createdOtp = otpService.generateOtp(otp.getIdentifier());
      String notification = NotificationMessageBuilder.builder(messageSource)
          .withOtp(createdOtp)
          .withLocale(Locale.forLanguageTag(otp.getLang()))
          .withNotificationType(NotificationType.SUCCESS_OTP_CREATED)
          .build();
      NotifierRegistry.notifyByAllEnabled(new Notification(otp.getTo(), notification));
      return ApiResponseBuilder.getSuccessOtpApiResponse(messageSource)
          .withOtp(createdOtp)
          .withLocale(Locale.forLanguageTag(otp.getLang())).build();
    } catch (OtpRequestLimitExceededException exceededException) {
      String notification = NotificationMessageBuilder.builder(messageSource)
          .withLocale(Locale.forLanguageTag(otp.getLang()))
          .withNotificationType(NotificationType.LIMIT_EXCEEDED)
          .build();
      NotifierRegistry.notifyByAllEnabled(new Notification(otp.getTo(), notification));
      throw exceededException;
    }

  }

  @PostMapping(value = "validate")
  public ResponseEntity<Response> validateOtp(
      @RequestBody final OtpNotify otp)
      throws OtpValidationLimitExceededException {
    try {
      Boolean validation = otpService.validateOtp(otp.getIdentifier(), otp.getOtp());
      NotificationType notificationType = validation ? NotificationType.SUCCESS_VALIDATION_OTP
          : NotificationType.FAILED_VALIDATION_OTP;
      String notification = NotificationMessageBuilder.builder(messageSource)
          .withLocale(Locale.forLanguageTag(otp.getLang()))
          .withNotificationType(notificationType)
          .build();
      NotifierRegistry.notifyByAllEnabled(new Notification(otp.getTo(), notification));
      return ApiResponseBuilder.getValidationApiResponseBuilder(messageSource)
          .withValidation(validation).withLocale(Locale.forLanguageTag(otp.getLang())).build();
    } catch (OtpValidationLimitExceededException exceededException) {
      String notification = NotificationMessageBuilder.builder(messageSource)
          .withLocale(Locale.forLanguageTag(otp.getLang()))
          .withNotificationType(NotificationType.LIMIT_EXCEEDED)
          .build();
      NotifierRegistry.notifyByAllEnabled(new Notification(otp.getTo(), notification));
      throw exceededException;
    }
  }

}
