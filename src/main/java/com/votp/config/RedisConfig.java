package com.votp.config;

import com.votp.exception.RedisConnectionEstablishException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

@Configuration
@Profile("redis")
public class RedisConfig {

  private final Logger logger = LoggerFactory.getLogger("RedisConfig");

  @Value("${redis.host:localhost}")
  private String host;

  @Value("${redis.port:6379}")
  private int port;

  @Value("${redis.password:redisPassword}")
  private String password;

  @Value("${redis.username:default}")
  private String username;

  @Value("${redis.timeout:5000}")
  private int timeout;

  @Bean
  public RedisConnectionFactory redisConnectionFactory() {
    RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
    redisStandaloneConfiguration.setHostName(host);
    redisStandaloneConfiguration.setUsername(username);
    redisStandaloneConfiguration.setPassword(password);
    redisStandaloneConfiguration.setPort(port);
    LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory(
        redisStandaloneConfiguration);
    connectionFactory.start();
    checkRedisConnection(connectionFactory);
    return connectionFactory;

  }

  public void checkRedisConnection(RedisConnectionFactory redisConnectionFactory) {
    try {
      logger.info("PING...");
      logger.info("{} Connected to Redis successfully.",
          redisConnectionFactory.getConnection().ping());
    } catch (Exception e) {
      logger.error("Could not connect to Redis: {}", e.getMessage());
      throw new RedisConnectionEstablishException(
          "Redis connection failed. Application will not start.", e);
    }
  }
}
