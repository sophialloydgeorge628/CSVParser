package parser;

import java.util.List;

/**
 * trivialCreator, a class which implements the creatorFromRow interface.
 * Given a row (a listOfStrings), trivialCreator's method create generates a
 * list of Strings
 */

public class TrivialCreator implements CreatorFromRow<List<String>> {

  /**
   * create, a method that reads a row from a CSV data source and returns a list of strings
   * @param row, a line in the CSV file
   * @return a list of strings, representing the contents of the row
   * @throws FactoryFailureException, conveys that, although the parser may have generated a list of
   * strings for the row, the data generated isn't of the form expected by the creator object
   */
  @Override
  public List<String> create(List<String> row) throws FactoryFailureException {
    return row;
  }
}
