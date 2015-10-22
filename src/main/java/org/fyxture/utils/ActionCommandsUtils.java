package org.fyxture.utils;

import org.fyxture.exception.*;
import java.util.*;
import java.util.regex.*;

public class ActionCommandsUtils {
  private static Pattern pattern = Pattern.compile("^(\\w+) (.*)$");

  public static String command(String line) {
    Matcher matcher = pattern.matcher(line);
    matcher.find();
    return matcher.group(1);
  }

  public static List<String> args(String line) throws QuotedArgBadFormatedException {
    Matcher matcher = pattern.matcher(line);
    matcher.find();
    return new Args(matcher.group(2)).all();
  }
}
