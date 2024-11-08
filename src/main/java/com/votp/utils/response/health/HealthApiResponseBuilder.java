package com.votp.utils.response.health;

import com.votp.models.Response;
import com.votp.models.Response.Payload;
import com.votp.utils.response.BuilderContract;
import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;

public class HealthApiResponseBuilder extends
    BuilderContract<ResponseEntity<Response>, HealthApiResponseBuilder> {

  private final MessageSource messageSource;
  private Locale locale;
  private String messageKey;

  public HealthApiResponseBuilder(MessageSource messageSource) {
    this.messageSource = messageSource;
  }

  public HealthApiResponseBuilder withLocale(Locale locale) {
    this.locale = locale;
    return this;
  }

  public HealthApiResponseBuilder withMessage(String messageKey) {
    this.messageKey = messageKey;
    return this;
  }

  @Override
  public ResponseEntity<Response> build() {
    return ResponseEntity.status(HTTP_STATUS).body(Response.builder()
        .isError(false)
        .payload(Payload.builder().message(
            messageSource.getMessage(messageKey, null,
                this.locale)
        ).build())
        .build());
  }
}
