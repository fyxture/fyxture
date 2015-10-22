package org.fyxture.utils;

import org.fyxture.exception.*;
import java.util.*;
import java.util.regex.*;
import org.apache.log4j.*;

public class Args {
  private Logger logger = Logger.getLogger(Args.class);
  private final String SINGLE_QUOTE = "'";
  private final String DOUBLE_QUOTE = "\"";
  private final String SINGLE_SPACE = " ";
  private int position = 0;
  private List<String> parts;
  private List<String> elements = new ArrayList<String>();

  public Args(String path) throws QuotedArgBadFormatedException {
    this.parts = Arrays.asList(path.split(" "));
    process();
  }

  public List<String> all() {
    return elements;
  }

  private void process() throws QuotedArgBadFormatedException {
    for(int length = parts.size(); position < length; position++) {
      String part = parts.get(position);
      if(part.isEmpty()) {
        continue;
      }
      if(part.startsWith(SINGLE_QUOTE) || part.startsWith(DOUBLE_QUOTE)) {
        if(part.startsWith(SINGLE_QUOTE)) {
          part = quoted(part, SINGLE_QUOTE);
        } else {
          part = quoted(part, DOUBLE_QUOTE);
        }
      }
      elements.add(part);
    }
  }

  private String quoted(String part, String character) throws QuotedArgBadFormatedException {
    try {
      part = part.substring(1);
      while(true) {
        position++;
        int index = parts.get(position).indexOf(character);
        if(index == -1) {
          part += SINGLE_SPACE + parts.get(position);
        } else {
          part += SINGLE_SPACE + parts.get(position).substring(0, index);
          break;
        }
      }
      return part;
    } catch(ArrayIndexOutOfBoundsException aioobe) {
      throw new QuotedArgBadFormatedException(part);
    }
  }
}
