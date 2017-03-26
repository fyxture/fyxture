package fyxture.rest;


import fyxture.FyxtureException;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpTrace;
import org.apache.http.client.methods.HttpRequestBase;


public class Trace extends FyxtureRestMethod {
  private Trace(String url) {
    super(url);
  }

  public static Trace create(String url) {
    return new Trace(url);
  }

  protected HttpRequestBase request() throws FyxtureException {
    try {
      return new HttpTrace(uriBuilder().build());
    } catch (URISyntaxException urise) {
      throw new FyxtureException(urise);
    }
  }
}
