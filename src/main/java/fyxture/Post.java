package fyxture;


import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.jose4j.json.internal.json_simple.JSONValue;
import org.yaml.snakeyaml.Yaml;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;


public class Post extends FyxtureRestMethod {
  private Post(String url) {
    super(url);
  }

  public static Post create(String url) {
    return new Post(url);
  }

  protected HttpEntityEnclosingRequestBase request() throws Throwable {
    return new HttpPost(uriBuilder().build());
  }
}
