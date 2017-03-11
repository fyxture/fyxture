package fyxture;


import java.io.File;

import org.apache.commons.io.FileUtils;


public class Fyxture {
  public static FyxtureDb db(String name) throws Throwable {
    return FyxtureDb.db(name);
  }

  public static FyxtureRest rest() {
    return new FyxtureRest();
  }

  public static FyxtureProperties properties(String name) throws Throwable {
    return FyxtureProperties.properties(name);
  }

  public static FyxtureFile file(String name) throws Throwable {
    return FyxtureFile.file(name);
  }

  public static FyxtureYaml yaml(String name) throws Throwable {
    return FyxtureYaml.yaml(name);
  }
  // 
  // public static FyxtureJson json(String name) throws Throwable {
  //   return FyxtureJson.json(name);
  // }
}
