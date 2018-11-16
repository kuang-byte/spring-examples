package com.hao.learn.springhazelcastcache.repository;

import com.hao.learn.springhazelcastcache.model.UserPayload;
import java.util.List;

public interface UserRepository {

  List<UserPayload> fetchAllUsers();

  UserPayload firstUser();

  UserPayload userByFirstNameAndLastName(String firstName, String lastName);
}
