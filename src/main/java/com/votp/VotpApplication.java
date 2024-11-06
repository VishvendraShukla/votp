package com.votp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;

@SpringBootApplication
public class VotpApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotpApplication.class, args);
	}

}
