package com.dawn.gonav.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class TimeTool {

  public static Date nowDate() {
    return Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());
  }

  public static long nowMilli() {
    return System.currentTimeMillis();
  }
}
