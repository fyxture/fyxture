package fyxture;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jose4j.json.internal.json_simple.JSONValue;
import org.yaml.snakeyaml.Yaml;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;


public class Put extends FyxtureRestMethod {
  private Put(String url) {
    super(url);
  }

  public static Put create(String url) {
    return new Put(url);
  }

  protected HttpEntityEnclosingRequestBase request() throws Throwable {
    return new HttpPut(uriBuilder().build());
  }
}
