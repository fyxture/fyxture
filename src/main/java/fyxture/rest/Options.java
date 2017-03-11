package fyxture.rest;


import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpRequestBase;


public class Options extends FyxtureRestMethod {
  private Options(String url) {
    super(url);
  }

  public static Options create(String url) {
    return new Options(url);
  }

  protected HttpRequestBase request() throws Throwable {
    return new HttpOptions(uriBuilder().build());
  }
}
