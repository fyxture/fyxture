package fyxture.rest;


import fyxture.Fyxture;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.logging.Logger;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONObject;
import org.yaml.snakeyaml.Yaml;


public abstract class FyxtureRestMethod {
  private static final Logger LOGGER = Logger.getLogger(FyxtureRestMethod.class.getName());
  protected String url;
  String path;
  String contentType;
  Map<String, String> params = new HashMap<String, String>();
  HttpResponse response;
  String responseContent;

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
    HttpRequestBase request = request();
    if(request instanceof HttpEntityEnclosingRequestBase) {
      entity((HttpEntityEnclosingRequestBase)request);
    }
    LOGGER.info(request.toString());

    DefaultHttpClient client = new DefaultHttpClient();
    response = client.execute(request);

    LOGGER.info(raw());
    client.getConnectionManager().shutdown();
    return this;
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

  protected abstract HttpRequestBase request() throws Throwable;

  protected URIBuilder uriBuilder() throws Throwable {
    URIBuilder builder = new URIBuilder(url);
    for(String key : params.keySet()) {
      builder.addParameter(key, params.get(key));
    }
    return builder;
  }

  protected FyxtureRestMethod(String url) {
    this.url = url;
  }

  private String content() throws Throwable {
    return Fyxture.file(path).content();
  }

  private String yamlTojson() throws Throwable {
    return Fyxture.yaml(path).json();
  }

  private void entity(HttpEntityEnclosingRequestBase request) throws Throwable {
    if(path != null) {
      StringEntity entity = new StringEntity(load());
      if(contentType != null) {
        entity.setContentType(contentType);
      }
      request.setEntity(entity);
    }
  }

  private String load() throws Throwable {
    return path.endsWith(".yml") ? yamlTojson() : content();
  }
}
