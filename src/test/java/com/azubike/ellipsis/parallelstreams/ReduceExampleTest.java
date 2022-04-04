package com.azubike.ellipsis.parallelstreams;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ReduceExampleTest {

  ReduceExample reduceExample = new ReduceExample();
  final List<Integer> input = List.of(1, 2, 3);

  @Test
  void reduceSum_parallelStream() {
    assertEquals(6, reduceExample.reduceSum_parallelStream(input));
  }

  @Test
  void reduceMultiply_parallelStream() {
    assertEquals(6, reduceExample.reduceMultiply_parallelStream(input));
  }
}
