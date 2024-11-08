package com.votp.webservices;

import com.votp.models.Notification;
import com.votp.models.Response;
import com.votp.notification.NotifierRegistry;
import com.votp.utils.response.ApiResponseBuilder;
import java.util.Locale;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/health")
@RequiredArgsConstructor
public class HealthRestController {

  private final Environment environment;
  private final ApplicationContext context;
  private final MessageSource messageSource;

  @GetMapping
  public ResponseEntity<Response> getHealth(
      @RequestParam(required = false, defaultValue = "en") final String lang) {
    String messageKey = "";
    if (environment.acceptsProfiles(Profiles.of("redis"))) {
      try {
        RedisConnectionFactory factory =
            (RedisConnectionFactory)
                this.context.getBean(RedisConnectionFactory.class);
        factory.getConnection().ping();
        messageKey = "message.health.redis.success.api";
      } catch (Exception e) {
        messageKey = "message.health.redis.fail.api";
      }
    } else {
      messageKey = "message.health.success.api";
    }
    return ApiResponseBuilder.getHealthApiResponseBuilder(messageSource)
        .withLocale(Locale.forLanguageTag(lang))
        .withMessage(messageKey)
        .build();
  }

  @GetMapping("/send")
  public String sendMessage() {
    NotifierRegistry.notifyByAllEnabled(new Notification("coolvishvendra14@gmail.com", "notification", "subject"));
    return "Hello";
  }

}
