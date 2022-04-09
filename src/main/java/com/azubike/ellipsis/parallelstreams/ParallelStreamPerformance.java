package com.azubike.ellipsis.parallelstreams;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;

public class ParallelStreamPerformance {
  public int sumUsingIntStream(int count, boolean isParallel) {

    startTimer();
    final IntStream intStream = IntStream.rangeClosed(0, count);
    if (isParallel) intStream.parallel();
    final int sum = intStream.sum();
    timeTaken();
    return sum;
  }

  public int sumUsingList(List<Integer> integers, boolean isParallel) {
    startTimer();
    final Stream<Integer> integerStream = integers.stream();
    if (isParallel) integerStream.parallel();
    final int sum = integerStream.mapToInt(Integer::intValue).sum();
    timeTaken();
    return sum;
  }

  public int sumUsingIterate(int n, boolean isParallel) {
    startTimer();
    final Stream<Integer> integerStream = Stream.iterate(0, i -> i + 1) ;
    if (isParallel) integerStream.parallel();
    final int sum = integerStream.limit(n + 1).reduce(0, Integer::sum);
    timeTaken();
    return sum;
  }
}
