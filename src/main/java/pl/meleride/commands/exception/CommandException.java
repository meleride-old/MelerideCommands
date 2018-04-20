package pl.meleride.commands.exception;

public class CommandException extends RuntimeException {

  public CommandException() {
    super();
  }

  public CommandException(String message) {
    super(message);
  }

  public CommandException(Throwable cause) {
    super(cause);
  }

  public CommandException(String message, Throwable cause) {
    super(message, cause);
  }

}
