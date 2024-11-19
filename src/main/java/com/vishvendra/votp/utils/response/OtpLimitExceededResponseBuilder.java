package com.vishvendra.votp.utils.response;

import com.vishvendra.votp.models.Response;
import com.vishvendra.votp.models.Response.Payload;
import java.util.Locale;
import java.util.Objects;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;

public class OtpLimitExceededResponseBuilder extends BuilderContract<ResponseEntity<Response>, OtpLimitExceededResponseBuilder> {

  private final MessageSource messageSource;
  private Locale locale;
  private Boolean isRequest;

  public OtpLimitExceededResponseBuilder(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public OtpLimitExceededResponseBuilder withLocale(Locale locale) {
    this.locale = locale;
    return this;
  }

  public OtpLimitExceededResponseBuilder isRequestLimitExceeded(Boolean isRequest) {
    this.isRequest = isRequest;
    return this;
  }

  @Override
  public ResponseEntity<Response> build() {
    if (Objects.isNull(this.locale)) {
      this.locale = LocaleContextHolder.getLocale();
    }

    if (Objects.isNull(this.isRequest)) {
      this.isRequest = Boolean.FALSE;
    }

    String messageKey =
        this.isRequest ? "limit.exceeded.otp.request" : "limit.exceeded.otp.validation";

    return ResponseEntity.status(HTTP_STATUS).body(Response.builder()
        .isError(true)
        .payload(Payload.builder().message(
            messageSource.getMessage(messageKey, null,
                this.locale)
        ).build())
        .build());
  }

}
