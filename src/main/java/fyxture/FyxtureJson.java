package fyxture;


import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;


public class FyxtureJson {
  private static Map<String, FyxtureJson> instances = new HashMap<String, FyxtureJson>();
  FyxtureFile file;
  JSONObject o;

  public static FyxtureJson json(String name) throws FyxtureException {
    if(!instances.containsKey(name)) {
      instances.put(name, new FyxtureJson(name));
    }
    return instances.get(name);
  }

  private FyxtureJson(String name) throws FyxtureException {
    this.file = Fyxture.file(name);
  }

  public JSONObject o() throws FyxtureException {
    return _o(null);
  }

  public JSONObject o(String encoding) throws FyxtureException {
    return _o(encoding);
  }

  private JSONObject _o(String encoding) throws FyxtureException {
    if(o == null) {
      this.o = new JSONObject(encoding == null ? file.content() : file.content(encoding));
    }
    return this.o;
  }
}
