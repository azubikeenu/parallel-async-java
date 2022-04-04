package com.azubike.ellipsis.parallelstreams;

import com.azubike.ellipsis.utils.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static com.azubike.ellipsis.utils.CommonUtil.startTimer;
import static com.azubike.ellipsis.utils.CommonUtil.timeTaken;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParallelStreamsExampleTest {
  ParallelStreamsExample parallelStreamsExample = new ParallelStreamsExample();

  @Test
  void stringTransform() {
    List<String> input = DataSet.namesList();
    startTimer();
    final List<String> result = parallelStreamsExample.stringTransform(input);
    timeTaken();
    assertEquals(4, result.size());
    result.forEach(
        name -> {
          assertTrue(name.contains("-"));
        });
  }

  @ParameterizedTest
  @ValueSource(booleans = {false, true})
  void stringTransform_1(boolean isParallel) {
    List<String> input = DataSet.namesList();
    startTimer();
    final List<String> result = parallelStreamsExample.stringTransform_1(input, isParallel);
    timeTaken();
    assertEquals(4, result.size());
    result.forEach(
        name -> {
          assertTrue(name.contains("-"));
        });
  }
}
