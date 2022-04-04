package com.azubike.ellipsis.parallelstreams;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;

public class LinkedListSpliteratorExample {
  public List<Integer> multiplyBy(LinkedList<Integer> input, int multiplier, boolean isParallel) {
    startTimer();
    final Stream<Integer> integerStream = input.stream();
    if (isParallel) integerStream.parallel();

    final List<Integer> returnedList =
        integerStream.map(val -> val * multiplier).collect(Collectors.toList());
    timeTaken();
    return returnedList;
  }
}
