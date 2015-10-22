package org.fyxture.utils;

import java.text.*;

public class StringUtils {
  public static String s(String inplace, Object... replacement) {
    return MessageFormat.format(inplace, replacement);
  }
}
