package fyxture;


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

  public static FyxtureProperties properties(String name) throws Throwable {
    if(!instances.containsKey(name)) {
      instances.put(name, new FyxtureProperties(name));
    }
    return instances.get(name);
  }

  public String get(String key) throws Throwable {
    if(properties == null) {
      loadProperties();
    }
    return properties.getProperty(key);
  }

  private void loadProperties() throws Throwable {
    properties = new Properties();
    properties.load(FyxtureFile.file(name + ".properties").stream());
  }
}
