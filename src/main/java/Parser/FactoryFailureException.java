package Parser;

import java.util.ArrayList;
import java.util.List;

/**
 * class for a FactoryFailureException, which communicates that,
 * although your parser might have generated a list-of-strings for the row,
 * the data wasn’t of the form the creator object expected.
 */
public class FactoryFailureException extends Exception {
  final List<String> row;

  public FactoryFailureException(String message, List<String> row) {
    super(message);
    this.row = new ArrayList<>(row);
  }
}
