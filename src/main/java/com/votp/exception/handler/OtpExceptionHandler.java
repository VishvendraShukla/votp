package com.votp.exception.handler;

import com.votp.exception.OtpException;
import com.votp.exception.OtpRequestLimitExceededException;
import com.votp.exception.OtpValidationLimitExceededException;
import com.votp.models.Response;
import com.votp.utils.response.ApiResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class OtpExceptionHandler extends ResponseEntityExceptionHandler {

  private final MessageSource messageSource;

  @ExceptionHandler(OtpValidationLimitExceededException.class)
  protected ResponseEntity<Response> handleOtpValidationLimitExceededException(
      OtpValidationLimitExceededException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return ApiResponseBuilder.getOtpLimitExceededResponseBuilder(messageSource)
        .isRequestLimitExceeded(false)
        .withHttpStatus(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler(OtpRequestLimitExceededException.class)
  protected ResponseEntity<Response> handleOtpRequestLimitExceededException(
      OtpRequestLimitExceededException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return ApiResponseBuilder.getOtpLimitExceededResponseBuilder(messageSource)
        .isRequestLimitExceeded(true)
        .withHttpStatus(HttpStatus.BAD_REQUEST)
        .build();
  }

  @ExceptionHandler(OtpException.class)
  protected ResponseEntity<Response> handleOtpException(
      OtpRequestLimitExceededException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return ApiResponseBuilder.getOtpLimitExceededResponseBuilder(messageSource)
        .isRequestLimitExceeded(false)
        .withHttpStatus(HttpStatus.BAD_REQUEST)
        .build();
  }


}
