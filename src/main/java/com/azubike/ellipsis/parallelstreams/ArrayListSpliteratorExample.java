package com.azubike.ellipsis.parallelstreams;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;

public class ArrayListSpliteratorExample {
  public List<Integer> multiplyBy(ArrayList<Integer> input, int multiplier, boolean isParallel) {
    startTimer();
    final Stream<Integer> integerStream = input.stream();
    if (isParallel) integerStream.parallel();

    final List<Integer> returnedList =
        integerStream.map(val -> val * multiplier).collect(Collectors.toList());
    timeTaken();
    return returnedList;
  }
}
