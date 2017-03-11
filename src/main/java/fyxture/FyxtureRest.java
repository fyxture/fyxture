package fyxture;


import fyxture.rest.Delete;
import fyxture.rest.Get;
import fyxture.rest.Head;
import fyxture.rest.Options;
import fyxture.rest.Post;
import fyxture.rest.Put;
import fyxture.rest.Trace;


public class FyxtureRest {
  public Delete delete(String url) {
    return Delete.create(url);
  }

  public Get get(String url) {
    return Get.create(url);
  }

  public Head head(String url) {
    return Head.create(url);
  }

  public Options options(String url) {
    return Options.create(url);
  }

  public Post post(String url) {
    return Post.create(url);
  }

  public Put put(String url) {
    return Put.create(url);
  }

  public Trace trace(String url) {
    return Trace.create(url);
  }
}
