package fyxture;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


public class FyxtureJson {
  private static Map<String, FyxtureJson> instances = new HashMap<String, FyxtureJson>();
  FyxtureFile file;
  JSONObject o;

  public static FyxtureJson json(String name) throws Throwable {
    if(!instances.containsKey(name)) {
      instances.put(name, new FyxtureJson(name));
    }
    return instances.get(name);
  }

  private FyxtureJson(String name) throws Throwable {
    this.file = Fyxture.file(name);
  }

  public JSONObject o() throws Throwable {
    return _o(null);
  }

  public JSONObject o(String encoding) throws Throwable {
    return _o(encoding);
  }

  private JSONObject _o(String encoding) throws Throwable {
    if(o == null) {
      this.o = new JSONObject(encoding == null ? file.content() : file.content(encoding));
    }
    return this.o;
  }
}
