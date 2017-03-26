package fyxture;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;


public class FyxtureYaml {
  private static Map<String, FyxtureYaml> instances = new HashMap<String, FyxtureYaml>();
  FyxtureFile file;

  public static FyxtureYaml yaml(String name) throws FyxtureException {
    if(!instances.containsKey(name)) {
      instances.put(name, new FyxtureYaml(name));
    }
    return instances.get(name);
  }

  private FyxtureYaml(String name) throws FyxtureException {
    this.file = Fyxture.file(name);
  }

  public Object o() throws FyxtureException {
    return new Yaml().load(file.content());
  }

  public Object o(String encoding) throws FyxtureException {
    return new Yaml().load(file.content(encoding));
  }

  public String json() throws FyxtureException {
    return JSONObject.valueToString(o());
  }

  public String json(String encoding) throws FyxtureException {
    return JSONObject.valueToString(o(encoding));
  }
}
