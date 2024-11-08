package com.votp.notification;

import com.votp.utils.response.BuilderContract;
import jakarta.validation.constraints.NotNull;
import java.util.Locale;
import java.util.Objects;
import lombok.SneakyThrows;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

public class NotificationMessageBuilder extends
    BuilderContract<String, NotificationMessageBuilder> {

  private final MessageSource messageSource;
  private Locale locale;
  private String otp;
  private NotificationType notificationType;

  public NotificationMessageBuilder(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public static NotificationMessageBuilder builder(MessageSource messageSource) {
    return new NotificationMessageBuilder(messageSource);
  }

  public NotificationMessageBuilder withLocale(Locale locale) {
    this.locale = locale;
    return this;
  }

  public NotificationMessageBuilder withNotificationType(NotificationType notificationType) {
    this.notificationType = notificationType;
    return this;
  }

  public NotificationMessageBuilder withOtp(@NotNull String otp) {
    this.otp = otp;
    return this;
  }

  @SneakyThrows
  @Override
  public String build() {
    if (Objects.isNull(this.locale)) {
      this.locale = LocaleContextHolder.getLocale();
    }
    return messageSource.getMessage(this.notificationType.value, new String[]{this.otp},
        this.locale);
  }

  public String toString() {
    return "SuccessOtpResponse.SuccessOtpResponseBuilder(locale=" + this.locale + ", otp="
        + this.otp + ")";
  }

  public enum NotificationType {
    SUCCESS_OTP_CREATED("message.otp.success.notification.created"),
    LIMIT_EXCEEDED("message.otp.notification.limit-exceeded"),
    SUCCESS_VALIDATION_OTP("message.otp.success.notification.validated"),
    FAILED_VALIDATION_OTP("message.otp.notification.invalidated");

    private final String value;

    NotificationType(String value) {
      this.value = value;
    }
  }


}
