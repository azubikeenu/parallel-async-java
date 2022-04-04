package com.azubike.ellipsis.parallelstreams;

import com.azubike.ellipsis.utils.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LinkedListSpliteratorExampleTest {
  LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

  @RepeatedTest(5)
  void multiplyBy_Sequential() {
    final int maxNumber = 1000000;
    final LinkedList<Integer> integers = DataSet.generateIntegerLinkedList(maxNumber);
    final List<Integer> returnedValue = linkedListSpliteratorExample.multiplyBy(integers, 2, false);
    assertEquals(maxNumber, returnedValue.size());
  }

  @RepeatedTest(5)
  void multiplyBy_Parallel() {
    final int maxNumber = 1000000;
    final LinkedList<Integer> integers = DataSet.generateIntegerLinkedList(maxNumber);
    final List<Integer> returnedValue = linkedListSpliteratorExample.multiplyBy(integers, 2, true);
    assertEquals(maxNumber, returnedValue.size());
  }
}
