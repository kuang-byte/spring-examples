package com.hao.learn.springhazelcastcache;

import com.hao.learn.springhazelcastcache.annotation.EnableLoadGenerator;
import com.hao.learn.springhazelcastcache.annotation.Man;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableLoadGenerator
public class SpringHazelcastCacheApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringHazelcastCacheApplication.class, args);
  }

  @Autowired
  private Man man;

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> man.print();
  }
}
