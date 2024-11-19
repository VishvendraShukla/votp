package com.vishvendra.votp.config;

import com.vishvendra.votp.service.OtpService;
import com.vishvendra.votp.service.OtpServiceImpl;
import com.vishvendra.votp.service.store.CachingOtpStore;
import com.vishvendra.votp.service.store.OtpStore;
import com.vishvendra.votp.service.store.RedisOtpStore;
import com.vishvendra.votp.service.throttle.OtpThrottleService;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class PlatformStarterConfiguration {

  private final Environment environment;

  @Autowired
  public PlatformStarterConfiguration(Environment environment) {
    this.environment = environment;
  }


  @Bean("redisOtpStore")
  @Profile("redis")
  public OtpStore createRedisOtpStore(@Value("${otp.length:5}") int otpLength) {
    return new RedisOtpStore(otpLength);
  }

  @Bean("cachingOtpStore")
  public OtpStore createCachingOtpStore(@Value("${otp.length:5}") int otpLength) {
    return new CachingOtpStore(otpLength);
  }

  @Bean("primaryOtpStore")
  public OtpStore createPrimaryOtpStore(@Value("${otp.length:5}") int otpLength) {
    if (environment.acceptsProfiles(Profiles.of("redis"))) {
      return createRedisOtpStore(otpLength);
    } else {
      return createCachingOtpStore(otpLength);
    }
  }

  @Bean("otpThrottleService")
  public OtpThrottleService otpThrottleService() {
    return new OtpThrottleService();
  }

  @Bean("otpService")
  public OtpService createOtpService(@Value("${otp.length:5}") int otpLength) {
    return new OtpServiceImpl(createPrimaryOtpStore(otpLength), otpThrottleService());
  }


  @Bean
  public MessageSource messageSource() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("internationalization/messages");
    messageSource.setDefaultEncoding("UTF-8");
    return messageSource;
  }

  @Bean
  public LocaleResolver localeResolver() {
    SessionLocaleResolver localeResolver = new SessionLocaleResolver();
    localeResolver.setDefaultLocale(Locale.ENGLISH);
    return localeResolver;
  }

}
