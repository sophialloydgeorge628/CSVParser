package parser;

import java.util.List;

/**
 * Skip Header Creator is a class which implements the interface CreatorFromRow. Through the
 * create method, it skips a header for a given CSV data source
 */
public class SkipHeaderCreator implements CreatorFromRow<List<String>> {

  /**
   * an initial state field, true if the line is the first (header) line and false otherwise
   */
  boolean state;

  /**
   * Constructor for SkipHeaderCreator, which sets state to true since the first line (header) has
   * not been read yet
   */
  public SkipHeaderCreator() {
    this.state = true;
  }

  /**
   * create method, which takes in a parsed row (a list of strings) and produces a list of strings.
   * @param row, a list of strings resulting from parsing each row of the CSV data source
   * @return a list of strings, representing the CSV data without the header
   * @throws FactoryFailureException, indicating that even if the parser generated
   * a list-of-strings for the row, the data wasn’t of the form the creator object expected.
   */
  @Override
  public List<String> create(List<String> row) throws FactoryFailureException {
    if (this.state) {
      // skip line
      this.state = false;
      return List.of();
    } else {
      return row;
    }
  }
}



