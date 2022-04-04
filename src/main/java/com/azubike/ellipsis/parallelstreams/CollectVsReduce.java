package com.azubike.ellipsis.parallelstreams;

import com.azubike.ellipsis.utils.DataSet;

import java.util.stream.Collectors;

public class CollectVsReduce {
  public static String collect() {
    return DataSet.namesList().parallelStream().collect(Collectors.joining());
  }

  public static String reduce() {
    return DataSet.namesList().parallelStream().reduce("", (s1, s2) -> s1 + s2);
  }

  public static void main(String[] args) {
    System.out.println("collect ->" + CollectVsReduce.collect());
    System.out.println("reduce -> " + CollectVsReduce.reduce());
  }
}
