package com.azubike.ellipsis.completable_future;

import com.azubike.ellipsis.services.HelloWorldService;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CompletableFuture;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CompletableFutureHelloWorldTest {
  final HelloWorldService hws = new HelloWorldService();
  CompletableFutureHelloWorld completableFutureHelloWorld = new CompletableFutureHelloWorld(hws);

  @Test
  void helloWorld() {
    final CompletableFuture<String> cf = completableFutureHelloWorld.helloWorld();
    cf.thenAccept(
            s -> {
              assertEquals("HELLO WORLD", s);
            })
        .join();
  }

  @Test
  void helloWorld_async_approach() {
    startTimer();
    final String output = completableFutureHelloWorld.helloWorld_async_approach();
    assertEquals("HELLO WORLD!", output);
    timeTaken();
  }

  @Test
  void helloWorld_approach_1() {
    startTimer();
    final String out = completableFutureHelloWorld.helloWorld_approach_1();
    assertEquals("hello world!", out);
    timeTaken();
  }

  @Test
  void helloWorld_async_approach_2() {
    startTimer();
    final String output = completableFutureHelloWorld.helloWorld_async_approach_2();
    assertEquals("HELLO WORLD! HI", output);
    timeTaken();
  }

  @Test
  void thenCompose() {
    startTimer();
    final CompletableFuture<String> output = completableFutureHelloWorld.thenCompose();
    output
        .thenAccept(
            s -> {
              assertEquals("HELLO WORLD!", s);
            })
        .join();
    timeTaken();
  }
}
