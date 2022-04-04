package com.azubike.ellipsis.parallelstreams;

import com.azubike.ellipsis.utils.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamPerformanceTest {
  ParallelStreamPerformance parallelStreamPerformance = new ParallelStreamPerformance();
  final int COUNT = 1_000_000;

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void sumUsingIntStream(boolean isParallel) {
    final int sum = parallelStreamPerformance.sumUsingIntStream(COUNT, isParallel);
    System.out.println(sum);
    assertTrue(sum > 0);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void sumUsingList(boolean isParallel) {
    final ArrayList<Integer> integers = DataSet.generateArrayList(COUNT);
    final int sum = parallelStreamPerformance.sumUsingList(integers, isParallel);
    System.out.println(sum);
    assertTrue(sum > 0);
  }

  @ParameterizedTest
  @ValueSource(booleans = {true, false})
  void sumUsingIterate(boolean isParallel) {
    final int sum = parallelStreamPerformance.sumUsingIterate(COUNT, isParallel);
    System.out.println(sum);
    assertTrue(sum > 0);

  }
}
