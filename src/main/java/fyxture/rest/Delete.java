package fyxture.rest;


import fyxture.FyxtureException;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;


public class Delete extends FyxtureRestMethod {
  private Delete(String url) {
    super(url);
  }

  public static Delete create(String url) {
    return new Delete(url);
  }

  protected HttpRequestBase request() throws FyxtureException {
    try {
      return new HttpDelete(uriBuilder().build());
    } catch (URISyntaxException urise) {
      throw new FyxtureException(urise);
    }
  }
}
