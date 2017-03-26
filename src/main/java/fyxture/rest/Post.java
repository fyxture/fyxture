package fyxture.rest;


import fyxture.FyxtureException;

import java.net.URISyntaxException;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;


public class Post extends FyxtureRestMethod {
  private Post(String url) {
    super(url);
  }

  public static Post create(String url) {
    return new Post(url);
  }

  protected HttpRequestBase request() throws FyxtureException {
    try {
      return new HttpPost(uriBuilder().build());
    } catch (URISyntaxException urise) {
      throw new FyxtureException(urise);
    }
  }
}
