package com.votp.config;

import com.votp.utils.HashCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Profile("redis")
public class HashingConfig {

  @Bean("hashCreator")
  public HashCreator createHashCreator() {
    return new HashCreator(passwordEncoder());
  }

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new Argon2PasswordEncoder(
        16,
        32,
        1,
        1 << 13,
        3
    );
  }

}
