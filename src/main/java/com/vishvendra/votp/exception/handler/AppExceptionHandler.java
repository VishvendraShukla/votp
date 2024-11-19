package com.vishvendra.votp.exception.handler;

import com.vishvendra.votp.exception.AppException;
import com.vishvendra.votp.exception.OtpRequestLimitExceededException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RequiredArgsConstructor
@ControllerAdvice
@Slf4j
public class AppExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler(AppException.class)
  protected ResponseEntity<Object> handleOtpException(
      OtpRequestLimitExceededException ex, WebRequest request) {
    log.error(ex.getMessage(), ex);
    return handleExceptionInternal(ex, "Application error",
        new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
  }

}
