package com.vishvendra.votp.webservices;

import com.vishvendra.votp.exception.OtpRequestLimitExceededException;
import com.vishvendra.votp.exception.OtpValidationLimitExceededException;
import com.vishvendra.votp.models.Otp;
import com.vishvendra.votp.models.Response;
import com.vishvendra.votp.service.OtpService;
import com.vishvendra.votp.utils.response.ApiResponseBuilder;
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
@RequestMapping("api/v1/otp")
@RequiredArgsConstructor
public class OtpRestController {


  private final OtpService otpService;
  private final MessageSource messageSource;

  @GetMapping(value = "create")
  public ResponseEntity<Response> createAndStoreOtp(
      @RequestParam(required = false, defaultValue = "en") final String lang,
      @RequestParam(name = "identifier") final String identifier)
      throws OtpRequestLimitExceededException {
    String createdOtp = otpService.generateOtp(identifier);
    return ApiResponseBuilder.getSuccessOtpApiResponse(messageSource)
        .withOtp(createdOtp)
        .withLocale(Locale.forLanguageTag(lang)).build();
  }

  @PostMapping(value = "validate")
  public ResponseEntity<Response> validateOtp(
      @RequestBody final Otp otp)
      throws OtpValidationLimitExceededException {
    Boolean validation = otpService.validateOtp(otp.getIdentifier(), otp.getOtp());
    return ApiResponseBuilder.getValidationApiResponseBuilder(messageSource)
        .withValidation(validation).withLocale(Locale.forLanguageTag(otp.getLang())).build();
  }

}
