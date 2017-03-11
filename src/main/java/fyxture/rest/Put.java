package fyxture.rest;


import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;


public class Put extends FyxtureRestMethod {
  private Put(String url) {
    super(url);
  }

  public static Put create(String url) {
    return new Put(url);
  }

  protected HttpRequestBase request() throws Throwable {
    return new HttpPut(uriBuilder().build());
  }
}
