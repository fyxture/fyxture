package fyxture.rest;


import fyxture.FyxtureException;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpRequestBase;


public class Get extends FyxtureRestMethod {
  private Get(String url) {
    super(url);
  }

  public static Get create(String url) {
    return new Get(url);
  }

  protected HttpRequestBase request() throws FyxtureException {
    try {
      return new HttpGet(uriBuilder().build());
    } catch (URISyntaxException urise) {
      throw new FyxtureException(urise);
    }
  }
}
