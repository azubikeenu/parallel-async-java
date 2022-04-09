package com.azubike.ellipsis.completable_future;

import com.azubike.ellipsis.services.HelloWorldService;

import java.util.concurrent.CompletableFuture;

import static com.azubike.ellipsis.utils.CommonUtil.delay;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class CompletableFutureHelloWorldExc {
  private HelloWorldService hws;

  public CompletableFutureHelloWorldExc(HelloWorldService hws) {
    this.hws = hws;
  }

  public String helloWorld_async_approach_handle() {
    CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
    CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
    CompletableFuture<String> hi =
        CompletableFuture.supplyAsync(
            () -> {
              delay(1000);
              return " hi";
            });

    final String output =
        hello
            .handle(
                (res, ex) -> {
                  if (ex != null) {
                    log("Exception : " + ex.getMessage());
                    return "";
                  }
                  return res;
                })
            .thenCombine(world, (h, w) -> h + w)
            .handle(
                (res, ex) -> {
                  if (ex != null) {
                    log("Exception : " + ex.getMessage());
                    return "";
                  }
                  return res;
                })
            .thenCombine(hi, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    return output;
  }

  public String helloWorld_async_approach_exceptionally() {
    CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
    CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
    CompletableFuture<String> hi =
        CompletableFuture.supplyAsync(
            () -> {
              delay(1000);
              return " hi";
            });

    final String output =
        hello
            .exceptionally(
                (ex) -> {
                  log("Exception hello :" + ex.getMessage());
                  return "";
                })
            .thenCombine(world, (h, w) -> h + w)
            .exceptionally(
                (ex) -> {
                  log("Exception world :" + ex.getMessage());
                  return "";
                })
            .thenCombine(hi, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    return output;
  }

  public String helloWorld_async_approach_when_complete() {
    CompletableFuture<String> hello = CompletableFuture.supplyAsync(hws::hello);
    CompletableFuture<String> world = CompletableFuture.supplyAsync(hws::world);
    CompletableFuture<String> hi =
        CompletableFuture.supplyAsync(
            () -> {
              delay(1000);
              return " hi";
            });

    final String output =
        hello
            .whenComplete(
                (res, ex) -> {
                  if (ex != null) {
                    log("Exception : " + ex.getMessage());
                  }
                })
            .thenCombine(world, (h, w) -> h + w)
            .whenComplete(
                (res, ex) -> {
                  if (ex != null) {
                    log("Exception : " + ex.getMessage());
                  }
                })
            .exceptionally(
                ex -> {
                  log("Exception :" + ex.getMessage());
                  return "";
                })
            .thenCombine(hi, (prev, curr) -> prev + curr)
            .thenApply(String::toUpperCase)
            .join();
    return output;
  }
}
