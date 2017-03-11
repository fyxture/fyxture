package fyxture.rest;


import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpRequestBase;


public class Trace extends FyxtureRestMethod {
  private Trace(String url) {
    super(url);
  }

  public static Trace create(String url) {
    return new Trace(url);
  }

  protected HttpRequestBase request() throws Throwable {
    return new HttpTrace(uriBuilder().build());
  }
}
