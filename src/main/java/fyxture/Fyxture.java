package fyxture;


import java.io.File;

import org.apache.commons.io.FileUtils;


public class Fyxture {
  public static FyxtureDb db(String name) throws FyxtureException {
    return FyxtureDb.db(name);
  }

  public static FyxtureRest rest(String url) {
    return new FyxtureRest(url);
  }

  public static FyxtureProperties properties(String name) throws FyxtureException {
    return FyxtureProperties.properties(name);
  }

  public static FyxtureFile file(String name) throws FyxtureException {
    return FyxtureFile.file(name);
  }

  public static FyxtureYaml yaml(String name) throws FyxtureException {
    return FyxtureYaml.yaml(name);
  }
  //
  // public static FyxtureJson json(String name) throws Throwable {
  //   return FyxtureJson.json(name);
  // }
}
