package com.hao.learn.springhazelcastcache.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hao.learn.springhazelcastcache.model.UserPayload;
import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
public class HazelcastCacheConfig {

  public static final String ALL_USERS_CACHE = "alluserscache";
  public static final String USER_CACHE = "usercache";

  @Autowired
  private ObjectMapper objectMapper;

  @Value("classpath:/users.json")
  private Resource usersJsonResource;

  @Bean
  public Config hazelCastConfig() {

    Config config = new Config();
    config.setInstanceName("hazelcast-cache");

    MapConfig allUsersCache = new MapConfig();
    allUsersCache.setTimeToLiveSeconds(200);
    allUsersCache.setEvictionPolicy(EvictionPolicy.LFU);
    config.getMapConfigs().put(ALL_USERS_CACHE, allUsersCache);

    MapConfig userCache = new MapConfig();
    userCache.setTimeToLiveSeconds(200);
    userCache.setEvictionPolicy(EvictionPolicy.LFU);
    config.getMapConfigs().put(USER_CACHE, userCache);

    return config;
  }

  @Bean
  public List<UserPayload> userPayloads() throws IOException {

    try (InputStream inputStream = usersJsonResource.getInputStream()) {

      UserPayload[] payloadUsers = objectMapper.readValue(inputStream, UserPayload[].class);
      return Collections.unmodifiableList(Arrays.asList(payloadUsers));
    }
  }
}
