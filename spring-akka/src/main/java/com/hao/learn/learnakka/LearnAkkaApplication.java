package com.hao.learn.learnakka;

import akka.actor.ActorSystem;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import sun.tools.jar.CommandLine;

@SpringBootApplication
public class LearnAkkaApplication {

  public static void main(String[] args) {
    SpringApplication.run(LearnAkkaApplication.class, args);
  }

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      ActorSystem system = ActorSystem.create("test-system");


    };
  }
}

