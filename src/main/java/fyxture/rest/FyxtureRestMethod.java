package fyxture.rest;


import fyxture.Fyxture;
import fyxture.FyxtureException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
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

  public FyxtureRestMethod body(String path) throws FyxtureException {
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

  public FyxtureRestMethod go() throws FyxtureException {
    HttpRequestBase request = request();
    if(request instanceof HttpEntityEnclosingRequestBase) {
      entity((HttpEntityEnclosingRequestBase)request);
    }
    LOGGER.info(request.toString());

    DefaultHttpClient client = new DefaultHttpClient();
    try {
      response = client.execute(request);
    } catch(IOException ioe) {
      throw new FyxtureException(ioe);
    }

    LOGGER.info(raw());
    client.getConnectionManager().shutdown();
    return this;
  }

  public HttpResponse response() {
    return response;
  }

  public String raw() throws FyxtureException {
    if(responseContent == null) {
      BufferedReader rd;
      StringBuffer result = new StringBuffer();
      try {
        rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
        String line = "";
        while ((line = rd.readLine()) != null) {
          result.append(line);
        }
      } catch (IOException ioe) {
        throw new FyxtureException(ioe);
      }
      responseContent = result.toString();
    }
    return responseContent;
  }

  public JSONObject json() throws FyxtureException {
    return new JSONObject(raw());
  }

  protected abstract HttpRequestBase request() throws FyxtureException;

  protected URIBuilder uriBuilder() throws FyxtureException {
    URIBuilder builder;
    try {
      builder = new URIBuilder(url);
    } catch (URISyntaxException urise) {
      throw new FyxtureException(urise);
    }
    for(String key : params.keySet()) {
      builder.addParameter(key, params.get(key));
    }
    return builder;
  }

  protected FyxtureRestMethod(String url) {
    this.url = url;
  }

  private String content() throws FyxtureException {
    return Fyxture.file(path).content();
  }

  private String yamlTojson() throws FyxtureException {
    return Fyxture.yaml(path).json();
  }

  private void entity(HttpEntityEnclosingRequestBase request) throws FyxtureException {
    if(path != null) {
      StringEntity entity;
      try {
        entity = new StringEntity(load());
      } catch (UnsupportedEncodingException uee) {
        throw new FyxtureException(uee);
      }
      if(contentType != null) {
        entity.setContentType(contentType);
      }
      request.setEntity(entity);
    }
  }

  private String load() throws FyxtureException {
    return path.endsWith(".yml") ? yamlTojson() : content();
  }
}
