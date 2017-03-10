package fyxture;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.impl.util.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.json.internal.json_simple.JSONValue;
import org.yaml.snakeyaml.Yaml;

import com.fasterxml.jackson.databind.ObjectMapper;


public abstract class FyxtureRestMethod {
  private static final Logger LOGGER = LogManager.getLogger(FyxtureRestMethod.class.getName());

  protected String url;
  String path;
  String contentType;
  Map<String, String> params = new HashMap<String, String>();
  HttpResponse response;
  String responseContent;

  protected FyxtureRestMethod(String url) {
    this.url = url;
  }

  private String content(String path) throws Throwable {
    return Fyxture.file(path).content();
  }

  private String yamlTojson(String path) throws Throwable {
    String content = content(path);
    Object yaml = new Yaml().load(content);
    String stringified = JSONValue.toJSONString(yaml);
    return stringified;
  }

  public FyxtureRestMethod body(String path) throws Throwable {
    this.path = path;
    return this;
  }

  public FyxtureRestMethod param(String key, String value) {
    params.put(key, value);
    return this;
  }

  public FyxtureRestMethod contentType(String contentType) {
    this.contentType = contentType;
    return this;
  }

  public FyxtureRestMethod go() throws Throwable {
    HttpEntityEnclosingRequestBase request = request();
    entity(request);
    LOGGER.info(request.toString());

    DefaultHttpClient client = new DefaultHttpClient();
    response = client.execute(request);

    LOGGER.info(raw());
    client.getConnectionManager().shutdown();
    return this;
  }

  protected abstract HttpEntityEnclosingRequestBase request() throws Throwable;

  private void entity(HttpEntityEnclosingRequestBase request) throws Throwable {
    if(path != null) {
      String stringified = path.endsWith(".yml") ? yamlTojson(path) : content(path);
      StringEntity entity = new StringEntity(stringified);
      if(contentType != null) {
        entity.setContentType(contentType);
      }
      request.setEntity(entity);
    }
  }

  protected URIBuilder uriBuilder() throws Throwable {
    URIBuilder builder = new URIBuilder(url);
    for(String key : params.keySet()) {
      builder.addParameter(key, params.get(key));
    }
    return builder;
  }

  public HttpResponse response() {
    return response;
  }

  public String raw() throws Throwable {
    if(responseContent == null) {
      BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      StringBuffer result = new StringBuffer();
      String line = "";
      while ((line = rd.readLine()) != null) {
        result.append(line);
      }
      responseContent = result.toString();
    }
    return responseContent;
  }

  public JSONObject json() throws Throwable {
    return new JSONObject(raw());
  }
}
