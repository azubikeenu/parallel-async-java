package com.azubike.ellipsis.parallelstreams;

import java.util.List;

public class ReduceExample {
  public int reduceSum_parallelStream(List<Integer> input) {
    return input.parallelStream().reduce(0, Integer::sum);
  }

  public int reduceMultiply_parallelStream(List<Integer> input) {
    return input.parallelStream().reduce(1, (x, y) -> x * y);
  }
}
