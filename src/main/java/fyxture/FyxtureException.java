package fyxture;


public class FyxtureException extends RuntimeException {
  public FyxtureException(String message) {
    super(message);
  }

  public FyxtureException(String message, Throwable e) {
    super(message, e);
  }

  public FyxtureException(Throwable e) {
    super(e);
  }
}
