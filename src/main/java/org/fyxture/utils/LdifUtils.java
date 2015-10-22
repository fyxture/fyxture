package org.fyxture.utils;

public class LdifUtils {
  public static String key(String line) {
    return line.replaceAll("=.*", "");
  }

  public static String value(String line) {
    return line.replaceAll("^[^=]+=", "");
  }
}
