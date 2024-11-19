package com.vishvendra.votp.repository;

import com.vishvendra.votp.entity.OtpCache;
import org.springframework.context.annotation.Profile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Profile("redis")
public interface OtpCacheRepository extends CrudRepository<OtpCache, String> {

  OtpCache findByIdentifier(final String identifier);

}
