package fyxture;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class FyxtureProperties {
  private static Map<String, FyxtureProperties> instances = new HashMap<String, FyxtureProperties>();
  private String name;
  private Properties properties;

  private FyxtureProperties(String name) {
    this.name = name;
  }

  public static FyxtureProperties properties(String name) throws FyxtureException {
    if(!instances.containsKey(name)) {
      instances.put(name, new FyxtureProperties(name));
    }
    return instances.get(name);
  }

  public String get(String key) throws FyxtureException {
    if(properties == null) {
      load();
    }
    return properties.getProperty(key);
  }

  private void load() throws FyxtureException {
    properties = new Properties();
    try {
      properties.load(FyxtureFile.file(name + ".properties").stream());
    } catch (IOException ioe) {
      throw new FyxtureException(ioe);
    }
  }
}
