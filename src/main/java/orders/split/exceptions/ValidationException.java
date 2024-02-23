package orders.split.exceptions;

public class ValidationException extends Throwable {
  public ValidationException(final String message) {
    super(message);
  }
}