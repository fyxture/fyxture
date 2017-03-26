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

  public Post post(String path) throws FyxtureException {
    return ((Post)(post().body(path)));
  }

  public Post post() {
    return Post.create(url);
  }

  public Put put(String path) throws FyxtureException {
    return ((Put)(put().body(path)));
  }

  public Put put() {
    return Put.create(url);
  }

  public Trace trace() {
    return Trace.create(url);
  }
}
