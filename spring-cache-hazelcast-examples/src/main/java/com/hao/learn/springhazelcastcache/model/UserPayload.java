package com.hao.learn.springhazelcastcache.model;

import java.io.Serializable;
import lombok.Data;

@Data
public class UserPayload implements Serializable {

  private String userName;
  private String firstName;
  private String lastName;
}
