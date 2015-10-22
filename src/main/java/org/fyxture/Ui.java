package org.fyxture;

import java.io.*;
import java.util.*;
import org.apache.commons.io.*;
import br.gov.frameworkdemoiselle.behave.internal.spi.*;
import br.gov.frameworkdemoiselle.behave.runner.*;
import org.openqa.selenium.*;
import org.fyxture.ui.*;
import org.fyxture.exception.*;
import org.fyxture.*;
import static org.fyxture.utils.StringUtils.s;

public class Ui extends Loadable {
  private final String path;
  private Runner runner = (Runner) InjectionManager.getInstance().getInstanceDependecy(Runner.class);
  private FyxtureUiBehaveAdapter adapter = FyxtureUiBehaveAdapterFactory.create();
  public Ui(Fyxture patern, Properties properties, String name) throws Throwable {
    super(patern);
    this.path = (String)properties.get("fyxture.ui." + name + ".screenshot.path");
  }

  private WebDriver driver() {
    return (WebDriver)runner.getDriver();
  }

  public Ui clean() throws Throwable {
    driver().manage().deleteAllCookies();
    return this;
  }

  public Ui start() throws Throwable {
    runner.start();
    return this;
  }

  public Ui stop() throws Throwable {
    runner.close();
    runner.quit();
    return this;
  }

  public Loadable remove(String... names) throws Throwable {
    throw new UnsupportedOperationException();
  }

  public Ui maximize() {
    driver().manage().window().maximize();
    return this;
  }

  public Ui screenshot(File file) throws IOException {
    FileUtils.copyFile(runner.saveScreenshot(), file);
    return this;
  }

  public Ui screenshot(String prefix, String suffix) throws IOException {
    prefix += prefix.equals("") ? "" : ".";
    suffix = (suffix.equals("") ? "" : ".") + suffix;
    if(path != null){
      File where = new File(path);
      if(!where.exists()) {
        where.mkdirs();
      }
      screenshot(File.createTempFile("fyxture.ui." + prefix, suffix + ".png", where));
    } else {
      screenshot(File.createTempFile("fyxture.ui." + prefix, suffix + ".png"));
    }
    return this;
  }

  public Ui screenshot() throws IOException {
    screenshot("", "");
    return this;
  }

  public Ui source(File file) throws IOException {
    FileUtils.writeStringToFile(file, runner.getPageSource());
    return this;
  }

  public Ui source() throws IOException {
    source("", "");
    return this;
  }

  public Ui source(String prefix, String suffix) throws IOException {
    prefix += prefix.equals("") ? "" : ".";
    suffix = (suffix.equals("") ? "" : ".") + suffix;
    if(path != null){
      File where = new File(path);
      if(!where.exists()) {
        where.mkdirs();
      }
      source(File.createTempFile("fyxture.ui.", ".html", where));
    } else {
      source(File.createTempFile("fyxture.ui.", ".html"));
    }
    return this;
  }

  public Loadable load(String name, Object... parameters) throws Throwable {
    String content = fyxture(name, parameters);
    File temp = File.createTempFile("ui.", ".fyxture");
    FileUtils.writeStringToFile(temp, content);
    adapter.interpret(FileUtils.readFileToString(temp));
    temp.deleteOnExit();
    return this;
  }

  public Ui on(String page) throws QuotedArgBadFormatedException {
    adapter.interpret(s("on {0}", page));
    return this;
  }

  public Ui visible(String text) throws QuotedArgBadFormatedException {
    adapter.interpret(s("visible {0}", text));
    return this;
  }
}
