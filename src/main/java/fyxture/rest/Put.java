package fyxture.rest;


import fyxture.FyxtureException;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;


public class Put extends FyxtureRestMethod {
  private Put(String url) {
    super(url);
  }

  public static Put create(String url) {
    return new Put(url);
  }

  protected HttpRequestBase request() throws FyxtureException {
    try {
      return new HttpPut(uriBuilder().build());
    } catch (URISyntaxException urise) {
      throw new FyxtureException(urise);
    }
  }
}
