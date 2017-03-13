package fyxture;


import fyxture.rest.Delete;
import fyxture.rest.Get;
import fyxture.rest.Head;
import fyxture.rest.Options;
import fyxture.rest.Post;
import fyxture.rest.Put;
import fyxture.rest.Trace;


public class FyxtureRest {
  private String url;

  public FyxtureRest(String url) {
    this.url = url;
  }

  public Delete delete() {
    return Delete.create(url);
  }

  public Get get() {
    return Get.create(url);
  }

  public Head head() {
    return Head.create(url);
  }

  public Options options() {
    return Options.create(url);
  }

  public Post post(String path) throws Throwable {
    return ((Post)(Post.create(url).body(path)));
  }

  public Put put(String path) throws Throwable {
    return ((Put)(Put.create(url).body(path)));
  }

  public Trace trace() {
    return Trace.create(url);
  }
}
