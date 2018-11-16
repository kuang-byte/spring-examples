package com.hao.learn.springhazelcastcache.repository;

import com.hao.learn.springhazelcastcache.config.HazelcastCacheConfig;
import com.hao.learn.springhazelcastcache.model.UserPayload;
import com.hazelcast.config.Config;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

@Repository(value = "userRepository")
@Slf4j
public class UserRepositoryHazelCast implements UserRepository {

  @Autowired
  private List<UserPayload> userPayloads;

  @Override
  @Cacheable(cacheNames = HazelcastCacheConfig.ALL_USERS_CACHE, key = "#root.methodName")
  public List<UserPayload> fetchAllUsers() {
    log.info("Fetching all users using hazelcast");
    return userPayloads;
  }

  @Override
  @Cacheable(cacheNames = HazelcastCacheConfig.USER_CACHE, key = "#root.methodName")
  public UserPayload firstUser() {
    log.info("Fetching firstUser using hazelcast");

    return userPayloads.get(0);
  }

  @Override
  @Cacheable(cacheNames = HazelcastCacheConfig.USER_CACHE, key = "{#firstName,#lastName}")
  public UserPayload userByFirstNameAndLastName(String firstName, String lastName) {
    log.info("Fetching user by firstname and lastname using hazelcast");

    Optional<UserPayload> users = userPayloads.stream().filter(
        userPayload -> userPayload.getFirstName().equals(firstName) &&
            userPayload.getLastName().equals(lastName)).findFirst();

    return users.isPresent() ? users.get() : null;
  }
}
