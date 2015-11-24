package org.fyxture;

import java.text.MessageFormat;
import java.util.*;
import java.util.regex.*;

public class Line {
  private String content;
  private String compiled;
  private boolean literal;
  private String file;
  private String args;

  public Line(String content) {
    this.content = content;
    this.compiled = "";
    process();
  }

  private void process() {
    String pattern = "^\\!(\\s*)(([\\w][\\w\\-]*)(\\/[\\w][\\w\\-]*)*)(\\s*)((\\:(.*))|(\\s*))$";
    Matcher matcher = Pattern.compile(pattern).matcher(content);
    if(!(this.literal = !matcher.find())) {
      this.file = matcher.group(2);
      this.args = matcher.group(8);
      compiled = '!' + this.file + ':' + (this.args == null ? "" : this.args);
    }else{
      compiled = content;
    }
  }

  public String fill(Object... parameters) {
    return MessageFormat.format(this.compiled, parameters);
  }

  public boolean literal() {
    return this.literal;
  }

  public String file() {
    return this.file;
  }

  public String args() {
    return this.args;
  }
}
