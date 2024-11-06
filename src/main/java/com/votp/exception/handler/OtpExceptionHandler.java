package com.votp.exception.handler;

import com.votp.exception.OtpException;
import com.votp.exception.OtpRequestLimitExceededException;
import com.votp.exception.OtpValidationLimitExceededException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@Slf4j
public class OtpExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(OtpValidationLimitExceededException.class)
  protected ResponseEntity<Object> handleOtpValidationLimitExceededException(
      OtpValidationLimitExceededException ex, WebRequest request) {
    String bodyOfResponse = "Otp Validation limit exceeded, please wait.";
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT, request);
  }

  @ExceptionHandler(OtpRequestLimitExceededException.class)
  protected ResponseEntity<Object> handleOtpRequestLimitExceededException(
      OtpRequestLimitExceededException ex, WebRequest request) {
    String bodyOfResponse = "Otp Request limit exceeded, please wait.";
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT, request);
  }

  @ExceptionHandler(OtpException.class)
  protected ResponseEntity<Object> handleOtpException(
      OtpRequestLimitExceededException ex, WebRequest request) {
    String bodyOfResponse = "Otp Request limit exceeded, please wait.";
    return handleExceptionInternal(ex, bodyOfResponse,
        new HttpHeaders(), HttpStatus.REQUEST_TIMEOUT, request);
  }


}
