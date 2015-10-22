package org.fyxture;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.io.*;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.*;

public abstract class Loadable {
  public abstract Loadable load(String name, Object... parameters) throws Throwable;
  public abstract Loadable remove(String... names) throws Throwable;
  protected Fyxture patern;

  protected Loadable(Fyxture patern) {
    this.patern = patern;
  }

  protected Fyxture end() {
    return patern;
  }

  protected File file(String name) throws Throwable {
    File file;
    try {
      file = new File(Loadable.class.getClassLoader().getResource(name + ".fyxture").getPath());
    } catch (Throwable t) {
      throw new Exception("Arquivo [" + name + "] não localizado.");
    }
    return file;
  }

  protected String fyxture(String name, Object... parameters) throws Throwable {
    File file = file(name);
    String content = FileUtils.readFileToString(file);
    content = compile(content, parameters);
    content = fill(content, parameters);
    return content;
  }

  protected String fill(String content, Object... parameters) throws Throwable {
    List<String> lines = Arrays.asList(content.split("\\n"));
    String result = "";
    for (String line : lines) {
      // result += MessageFormat.format(line.replaceAll("'", "''"), parameters) + "\n";
      result += getLine(line, parameters);
    }
    return result;
  }

  protected String compile(String content, Object... parameters) throws Throwable {
    List<String> lines = Arrays.asList(content.split("\\n"));
    String result = "";
    String pattern = "^\\!(([\\w][\\w\\-]*)(\\/[\\w][\\w\\-]*)*)\\:(.*)$";
    for (String line : lines) {
      Matcher matcher = Pattern.compile(pattern).matcher(line);
      if (matcher.find()) {
        result += indirection(matcher.group(1), matcher.group(4));
      } else {
        result += getLine(line);
      }
    }
    return result;
  }

  protected String getLine(String line, Object... parameters) {
    String result = "";
    String temp = line.replaceAll("'", "''");
    Matcher m2 = Pattern.compile("\\{\\d+\\}").matcher(temp);
    if (m2.find()) {
      result += MessageFormat.format(temp, parameters) + "\n";
    } else {
      result += line + "\n";
    }
    return result;
  }

  protected String indirection(String name, String parameters) throws Throwable {
    String pattern = "([^,]+)";
    Matcher matcher = Pattern.compile(pattern).matcher(parameters);
    List params = new ArrayList();
    while (matcher.find()) {
      params.add(matcher.group(1).trim());
    }
    return compile(FileUtils.readFileToString(file(name)), params.toArray());
  }
}
