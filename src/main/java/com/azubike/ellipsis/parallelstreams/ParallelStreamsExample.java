package com.azubike.ellipsis.parallelstreams;

import com.azubike.ellipsis.utils.DataSet;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.azubike.ellipsis.utils.CommonUtil.*;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class ParallelStreamsExample {

  public List<String> stringTransform(List<String> input) {
    return input
        // .stream()
        .parallelStream()
        .map(this::addNameLengthTransform)
        .collect(Collectors.toList());
  }

  public List<String> stringTransform_1(List<String> input, boolean isParallel) {
    final Stream<String> stringStream = input.stream();
    if (isParallel) stringStream.parallel();

    return stringStream.map(this::addNameLengthTransform).collect(Collectors.toList());
  }

  public static void main(String[] args) {
    final List<String> names = DataSet.namesList();
    startTimer();
    final List<String> resultList = new ParallelStreamsExample().stringTransform(names);
    log("resultList" + resultList);
    timeTaken();
  }

  private String addNameLengthTransform(String name) {
    delay(500);
    return name.length() + " - " + name;
  }
}
