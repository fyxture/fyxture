package fyxture;

public class FyxtureRest {
  public Post post(String url) {
    return Post.create(url);
  }

  public Put put(String url) {
    return Put.create(url);
  }
}
