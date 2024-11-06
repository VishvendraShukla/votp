package com.votp.utils.response.successotp;

import com.votp.models.Response;
import com.votp.models.Response.Payload;
import com.votp.utils.response.BuilderContract;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;

public class SuccessOtpApiResponseBuilder implements BuilderContract<Response> {

  private final MessageSource messageSource;
  private Locale locale;
  private String otp;

  public SuccessOtpApiResponseBuilder(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public SuccessOtpApiResponseBuilder withLocale(Locale locale) {
    this.locale = locale;
    return this;
  }

  public SuccessOtpApiResponseBuilder withOtp(@NotNull String otp) {
    this.otp = otp;
    return this;
  }

  @SneakyThrows
  @Override
  public ResponseEntity<Response> build() {
    if (Objects.isNull(this.locale)) {
      this.locale = LocaleContextHolder.getLocale();
    }
    if (Objects.isNull(this.otp)) {
//      TODO add a proper exception
      throw new Exception();
    }

    return ResponseEntity.ok(Response.builder()
        .isError(false)
        .payload(Payload.builder().message(
            messageSource.getMessage("message.otp.success.api.created", new Object[]{this.otp},
                this.locale)
        ).build())
        .build());
  }

  public String toString() {
    return "SuccessOtpResponse.SuccessOtpResponseBuilder(locale=" + this.locale + ", otp="
        + this.otp + ")";
  }
}
