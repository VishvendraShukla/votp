package com.votp.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;

@RedisHash("OtpCache")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Profile("redis")
public class OtpCache {

  @Id
  private String identifier;
  private String otpHash;
  @TimeToLive
  private Long ttl;
}
