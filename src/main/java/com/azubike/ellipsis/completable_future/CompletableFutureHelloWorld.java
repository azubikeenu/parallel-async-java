package com.azubike.ellipsis.completable_future;

import com.azubike.ellipsis.services.HelloWorldService;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static com.azubike.ellipsis.utils.CommonUtil.*;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class CompletableFutureHelloWorld {
  private HelloWorldService hws;

  public CompletableFutureHelloWorld(HelloWorldService hws) {
    this.hws = hws;
  }

  public CompletableFuture<String> helloWorld() {
    return CompletableFuture.supplyAsync(hws::helloWorld).thenApply(String::toUpperCase);
  }

  public String helloWorld_approach_1() {
    String hello = hws.hello();
    String world = hws.world();
    return hello + world;
  }

  public String helloWorld_async_approach() {
    CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
    CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
    return hello.thenCombine(world, (h, w) -> h + w).thenApply(String::toUpperCase).join();
  }

  public String helloWorld_async_approach_2() {
    CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
    CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
    CompletableFuture<String> hi =
        CompletableFuture.supplyAsync(
            () -> {
              delay(1000);
              return " hi";
            });
    return hello
        .thenCombine(world, (h, w) -> h + w)
        .thenCombine(hi, (prev, curr) -> prev + curr)
        .thenApply(String::toUpperCase)
        .join();
  }

  // this is usually applied on non-blocking calls that have relative dependencies
  public CompletableFuture<String> thenCompose() {
    return CompletableFuture.supplyAsync(hws::hello)
        .thenCompose(hws::worldFuture)
        .thenApply(String::toUpperCase);
  }

  public static void main(String[] args) {
    HelloWorldService hws = new HelloWorldService();
    CompletableFuture.supplyAsync(hws::helloWorld) // initiates the async task
        .thenApply(String::toUpperCase) // mutates the result of the task
        .thenAccept(System.out::println) // consumes the result of the class
        .join(); // blocks the main-thread till the completion of the task
    log("Done");
    // delay(2000);
    Supplier<String> hello = hws::hello;
    Supplier<String> world = hws::world;
    List<Supplier<String>> supplierList = List.of(hello, world);

    startTimer();
    final String output =
        supplierList.parallelStream().map(Supplier::get).collect(Collectors.joining());
    System.out.println(output);
    timeTaken();
  }
}
