package com.vishvendra.votp.utils.startup;

import com.vishvendra.votp.exception.RedisConnectionEstablishException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

  private final Environment environment;
  private final RedisConnectionFactory redisConnectionFactory;

  @Override
  public void run(String... args) throws Exception {

    log.info("Application started with command-line arguments: {}", (Object[]) args);

    if (environment.acceptsProfiles(Profiles.of("redis"))) {
      log.info("Redis profile is active.");
      checkRedisConnection(redisConnectionFactory);
    } else {
      log.info("No profile is active, Caching is configured to Guava.");
    }
  }

  public void checkRedisConnection(RedisConnectionFactory redisConnectionFactory) {
    try {
      log.info("Testing redis connection: PING...");
      log.info("{} Connected to Redis successfully.",
          redisConnectionFactory.getConnection().ping());
    } catch (Exception e) {
      log.error("Could not connect to Redis: {}", e.getMessage());
      throw new RedisConnectionEstablishException(
          "Redis connection failed. Application will not start.", e);
    }
  }
}
