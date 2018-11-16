package com.hao.learn.myannotation;

import com.hao.learn.myannotation.print.Print;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class MyAnnotationApplication {

  public static void main(String[] args) {
    SpringApplication.run(MyAnnotationApplication.class, args);
  }

  @Autowired
  private Print printCar;
  @Autowired
  private Print printHouse;

  @Bean
  public CommandLineRunner commandLineRunner() {
    return args -> {
      printCar.print();
      printHouse.print();
    };
  }
}
