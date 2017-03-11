package fyxture.rest;


import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpRequestBase;


public class Head extends FyxtureRestMethod {
  private Head(String url) {
    super(url);
  }

  public static Head create(String url) {
    return new Head(url);
  }

  protected HttpRequestBase request() throws Throwable {
    return new HttpHead(uriBuilder().build());
  }
}
