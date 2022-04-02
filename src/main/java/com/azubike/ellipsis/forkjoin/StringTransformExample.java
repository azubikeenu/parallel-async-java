package com.azubike.ellipsis.forkjoin;

import com.azubike.ellipsis.utils.DataSet;

import java.util.ArrayList;
import java.util.List;

import static com.azubike.ellipsis.utils.CommonUtil.delay;
import static com.azubike.ellipsis.utils.CommonUtil.stopWatch;
import static com.azubike.ellipsis.utils.LoggerUtil.log;

public class StringTransformExample {

  public static void main(String[] args) {

    stopWatch.start();
    List<String> resultList = new ArrayList<>();
    List<String> names = DataSet.namesList();
    log("names : " + names);

    names.forEach(
        (name) -> {
          String newValue = addNameLengthTransform(name);
          resultList.add(newValue);
        });
    stopWatch.stop();
    log("Final Result : " + resultList);
    log("Total Time Taken : " + stopWatch.getTime());
  }

  private static String addNameLengthTransform(String name) {
    delay(500);
    return name.length() + " - " + name;
  }
}
