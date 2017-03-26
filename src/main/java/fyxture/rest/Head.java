package fyxture.rest;


import fyxture.FyxtureException;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpRequestBase;


public class Head extends FyxtureRestMethod {
  private Head(String url) {
    super(url);
  }

  public static Head create(String url) {
    return new Head(url);
  }

  protected HttpRequestBase request() throws FyxtureException {
    try {
      return new HttpHead(uriBuilder().build());
    } catch (URISyntaxException urise) {
      throw new FyxtureException(urise);
    }
  }
}
