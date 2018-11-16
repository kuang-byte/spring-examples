package com.hao.learn.springhazelcastcache.controller;

import com.hao.learn.springhazelcastcache.annotation.EnableLoadGenerator;
import com.hao.learn.springhazelcastcache.config.HazelcastCacheConfig;
import com.hao.learn.springhazelcastcache.model.UserPayload;
import com.hao.learn.springhazelcastcache.repository.UserRepository;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UsersController {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private HazelcastInstance hazelcastInstancel;

  @RequestMapping(path = "/users/all", method = RequestMethod.GET)
  public List<UserPayload> fetchUsers() {
    return userRepository.fetchAllUsers();
  }

  @RequestMapping(path = "/users/first", method = RequestMethod.GET)
  public UserPayload fetchFirst() {
    return userRepository.firstUser();
  }

  @RequestMapping(path = "/users?firstName={firstName}&&lastName={lastName}", method = RequestMethod.GET)
  public UserPayload findByFirstNameLastName(@PathVariable("firstName") String firstName,
      @PathVariable("lastName") String lastName) {
    return userRepository.userByFirstNameAndLastName(firstName, lastName);
  }

  @GetMapping("/user-in-cache")
  public void printUserInCache() {
    // fetch first user in the cache
    IMap<String, UserPayload> userMap = hazelcastInstancel.getMap(HazelcastCacheConfig.USER_CACHE);
    userMap.entrySet().stream().forEach(
        entry ->
            System.out.println(entry.getKey() + " " + entry.getValue())
    );
    // fetch all users in the cache
    System.out.println(hazelcastInstancel
        .getMap(HazelcastCacheConfig.ALL_USERS_CACHE).get("fetchAllUsers").toString());
  }
}

