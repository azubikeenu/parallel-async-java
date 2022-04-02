package com.azubike.ellipsis.forkjoin;

import com.azubike.ellipsis.utils.DataSet;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.azubike.ellipsis.utils.CommonUtil.stopWatch;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {
  private List<String> input;

  public ForkJoinUsingRecursion(List<String> input) {
    this.input = input;
  }

  public static void main(String[] args) {
    stopWatch.start();
    List<String> names = DataSet.namesList();
    log("names : " + names);
    ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
    ForkJoinPool pool = ForkJoinPool.commonPool();
    final List<String> resultList = pool.invoke(forkJoinUsingRecursion);
    stopWatch.stop();
    log("Final Result : " + resultList);
    log("Total Time Taken : " + stopWatch.getTime());
  }

  private List<String> transformList() {
    return this.input.stream()
        .map(ForkJoinUsingRecursion::addNameLengthTransform)
        .collect(Collectors.toList());
  }

  private static String addNameLengthTransform(String name) {
    return name.length() + " - " + name.toUpperCase();
  }

  @Override
  protected List<String> compute() {
    System.out.println(Thread.currentThread());

    int midpoint = input.size() / 2;
    if (input.size() > 1) {
      final ForkJoinUsingRecursion taskOne = new ForkJoinUsingRecursion(input.subList(0, midpoint));
      final ForkJoinUsingRecursion taskTwo =
          new ForkJoinUsingRecursion(input.subList(midpoint, input.size()));
      taskOne.fork();
      taskTwo.fork();
      return Stream.concat(taskOne.join().stream(), taskTwo.join().stream())
          .collect(Collectors.toList());

    } else {
      return transformList();
    }
  }
}
