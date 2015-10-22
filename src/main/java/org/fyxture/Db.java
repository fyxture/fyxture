package org.fyxture;

import java.io.*;
import java.text.*;
import java.util.*;
import java.util.regex.*;
import org.apache.commons.io.*;
import org.apache.tools.ant.taskdefs.*;
import org.apache.tools.ant.*;

public class Db extends Loadable {
  private final String driver;
  private final String url;
  private final String username;
  private final String password;

  public Db(Fyxture patern, Properties properties, String name) throws Throwable {
    super(patern);
    this.driver = (String)properties.get("fyxture.db." + name + ".driver");
    this.url = (String)properties.get("fyxture.db." + name + ".url");
    this.username = (String)properties.get("fyxture.db." + name + ".username");
    this.password = (String)properties.get("fyxture.db." + name + ".password");
  }

  public Loadable remove(String... names) throws Throwable {
    throw new UnsupportedOperationException();
  }

  public Loadable load(String name, Object... parameters) throws Throwable {
    String content = fyxture(name, parameters);
    File temp = File.createTempFile("db.", ".fyxture");
    FileUtils.writeStringToFile(temp, content);
    execute(temp);
    temp.deleteOnExit();
    return this;
  }

  private void execute(File file) throws Throwable {
    final class Executor extends SQLExec {
      public Executor() {
        Project project = new Project();
        project.init();
        setProject(project);
        setTaskType("sql");
        setTaskName("sql");
      }
    }
    Executor executor = new Executor();
    executor.setDriver(driver);
    executor.setUrl(url);
    executor.setUserid(username);
    executor.setPassword(password);
    executor.setSrc(file);
    executor.addText("");
    executor.execute();
  }
}
