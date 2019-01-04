package com.hao.learn;

import com.lmax.disruptor.BusySpinWaitStrategy;
import com.lmax.disruptor.EventFactory;
import com.lmax.disruptor.EventHandler;
import com.lmax.disruptor.RingBuffer;
import com.lmax.disruptor.dsl.Disruptor;
import com.lmax.disruptor.dsl.ProducerType;
import com.lmax.disruptor.util.DaemonThreadFactory;
import java.util.stream.IntStream;
import lombok.Getter;
import lombok.Setter;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;

@State(Scope.Benchmark)
public class Main {

  public static void main(String[] args) {
    Disruptor<ValueEvent> disruptor = new Disruptor<>(ValueEvent.EVENT_FACTORY, 16,
        DaemonThreadFactory.INSTANCE, ProducerType.SINGLE, new BusySpinWaitStrategy());

    disruptor.handleEventsWith(SingleEventPrintConsumer.getEventHandler());

    RingBuffer<ValueEvent> ringBuffer = disruptor.start();

    IntStream.range(0, 10).forEach(
        index -> {
          long sequenceId = ringBuffer.next();
          ValueEvent valueEvent = ringBuffer.get(sequenceId);
          valueEvent.setValue(index);
          valueEvent.setAnotherValue("Hello world");
          ringBuffer.publish(sequenceId);
        }
    );
  }

  public static class SingleEventPrintConsumer {

    public static EventHandler<ValueEvent>[] getEventHandler() {
      EventHandler<ValueEvent> printEventHandler =
          (event, sequence, endOfBatch) -> print(event.getValue(), event.getAnotherValue(),
              sequence);
      return new EventHandler[]{printEventHandler};
    }

    public static void print(int id, String anotherValue, long sequenceId) {
      System.out.println(
          "Id is " + id + "  sequenceId is " + sequenceId + " anotherValue " + anotherValue);
    }
  }

  @Getter
  @Setter
  public static class ValueEvent {

    private int value;
    private String anotherValue;

    public final static EventFactory<ValueEvent> EVENT_FACTORY = () -> new ValueEvent();
  }

}
