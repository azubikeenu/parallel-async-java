package com.azubike.ellipsis.parallelstreams;

import com.azubike.ellipsis.utils.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ArrayListSpliteratorExampleTest {
  ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

  @RepeatedTest(5)
  void multiplyBy_Sequential() {
    final int maxNumber = 1000000;
    final ArrayList<Integer> integers = DataSet.generateArrayList(maxNumber);
    final List<Integer> returnedValue = arrayListSpliteratorExample.multiplyBy(integers, 2 , false);
    assertEquals(maxNumber, returnedValue.size());
  }

  @RepeatedTest(5)
  void multiplyBy_Parallel() {
    final int maxNumber = 1000000;
    final ArrayList<Integer> integers = DataSet.generateArrayList(maxNumber);
    final List<Integer> returnedValue = arrayListSpliteratorExample.multiplyBy(integers, 2 , true);
    assertEquals(maxNumber, returnedValue.size());
  }

}


