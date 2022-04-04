package com.azubike.ellipsis.parallelstreams;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class ParallelStreamsResultOrder {
  public static List<Integer> listOrder(List<Integer> inputList) {
    return inputList.parallelStream().map(integer -> integer * 2).collect(Collectors.toList());
  }

  public static Set<Integer> setOrder(Set<Integer> inputList) {
    return inputList.parallelStream().map(integer -> integer * 2).collect(Collectors.toSet());
  }

  public static void main(String[] args) {

    final List<Integer> integers = List.of(4, 5, 6, 7, 8);
    log("input" + integers);
    final List<Integer> resultsOrder = ParallelStreamsResultOrder.listOrder(integers);
    log("result" + resultsOrder);

    final Set<Integer> sets = Set.of(2, 4, 6, 8, 10);
    log("input" + sets);
    final Set<Integer> setOrder = ParallelStreamsResultOrder.setOrder(sets);
    log("result" + setOrder);

  }
}
