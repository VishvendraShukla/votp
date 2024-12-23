package com.vishvendra.votp.utils.response.validation;

import com.vishvendra.votp.models.Response;
import com.vishvendra.votp.models.Response.Payload;
import com.vishvendra.votp.utils.response.BuilderContract;
import java.util.Locale;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;

public class ValidationApiResponseBuilder extends BuilderContract<ResponseEntity<Response>, ValidationApiResponseBuilder> {

  private final MessageSource messageSource;
  private Locale locale;
  private Boolean isValidated;

  public ValidationApiResponseBuilder(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public ValidationApiResponseBuilder withLocale(Locale locale) {
    this.locale = locale;
    return this;
  }

  public ValidationApiResponseBuilder withValidation(Boolean isValidated) {
    this.isValidated = isValidated;
    return this;
  }

  @SneakyThrows
  @Override
  public ResponseEntity<Response> build() {
    if (Objects.isNull(this.locale)) {
      this.locale = LocaleContextHolder.getLocale();
    }

    if (Objects.isNull(this.isValidated)) {
      this.isValidated = Boolean.FALSE;
    }

    String messageKey =
        this.isValidated ? "message.otp.success.api.validated" : "message.otp.api.invalidated";

    return ResponseEntity.status(HTTP_STATUS).body(Response.builder()
        .isError(false)
        .payload(Payload.builder().message(
            messageSource.getMessage(messageKey, null,
                this.locale)
        ).build())
        .build());
  }
}
