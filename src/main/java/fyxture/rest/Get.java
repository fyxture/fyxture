package fyxture.rest;


import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;


public class Get extends FyxtureRestMethod {
  private Get(String url) {
    super(url);
  }

  public static Get create(String url) {
    return new Get(url);
  }

  protected HttpRequestBase request() throws Throwable {
    return new HttpGet(uriBuilder().build());
  }
}
