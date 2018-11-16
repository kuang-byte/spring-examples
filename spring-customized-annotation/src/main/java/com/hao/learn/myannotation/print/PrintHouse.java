package com.hao.learn.myannotation.print;

import com.hao.learn.myannotation.annotation.AutoPrint;
import lombok.extern.slf4j.Slf4j;

@AutoPrint
@Slf4j
public class PrintHouse implements Print {

  @Override
  public void print() {
    log.info("I am House");
  }
}
