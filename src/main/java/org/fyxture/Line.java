package org.fyxture;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.*;

public class Line {
  private String content;
  private String compiled;
  private boolean literal;

  public Line(String content) {
    this.content = content;
    this.compiled = "";
    process();
  }


  private void process() {
    String pattern = "^\\!(([\\w][\\w\\-]*)(\\/[\\w][\\w\\-]*)*)((\\:(.*))|(\\s+))$";
    Matcher matcher = Pattern.compile(pattern).matcher(content);
    literal = !matcher.find();
    compiled = content;
  }

  public String fill(Object... parameters) {
    return MessageFormat.format(this.compiled, parameters);
  }

  public boolean literal() {
    return literal;
  }
}
