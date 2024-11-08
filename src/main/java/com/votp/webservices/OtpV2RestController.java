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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v2/otp")
@RequiredArgsConstructor
public class OtpV2RestController {

  private final OtpService otpService;
  private final MessageSource messageSource;

  @GetMapping(value = "create")
  public ResponseEntity<Response> createAndStoreOtp(
      @RequestParam(required = false, defaultValue = "en") final String lang,
      @RequestParam(name = "identifier") final String identifier,
      @RequestParam(name = "to") final String to)
      throws OtpRequestLimitExceededException {
    String createdOtp = otpService.generateOtp(identifier);
    String notification = NotificationMessageBuilder.builder(messageSource)
        .withOtp(createdOtp)
        .withLocale(Locale.forLanguageTag(lang))
        .withNotificationType(NotificationType.SUCCESS_OTP_CREATED)
        .build();
    NotifierRegistry.notifyByAllEnabled(new Notification(to, notification));
    return ApiResponseBuilder.getSuccessOtpApiResponse(messageSource)
        .withOtp(createdOtp)
        .withLocale(Locale.forLanguageTag(lang)).build();
  }

  @PostMapping(value = "validate")
  public ResponseEntity<Response> validateOtp(
      @RequestBody final OtpNotify otp)
      throws OtpValidationLimitExceededException {
    Boolean validation = otpService.validateOtp(otp.getIdentifier(), otp.getOtp());
    String notification = NotificationMessageBuilder.builder(messageSource)
        .withLocale(Locale.forLanguageTag(otp.getLang()))
        .withNotificationType(NotificationType.SUCCESS_VALIDATION_OTP)
        .build();
    NotifierRegistry.notifyByAllEnabled(new Notification(otp.getTo(), notification));
    return ApiResponseBuilder.getValidationApiResponseBuilder(messageSource)
        .withValidation(validation).withLocale(Locale.forLanguageTag(otp.getLang())).build();
  }

}
