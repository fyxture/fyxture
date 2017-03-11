package fyxture.rest;


import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpRequestBase;


public class Delete extends FyxtureRestMethod {
  private Delete(String url) {
    super(url);
  }

  public static Delete create(String url) {
    return new Delete(url);
  }

  protected HttpRequestBase request() throws Throwable {
    return new HttpDelete(uriBuilder().build());
  }
}
