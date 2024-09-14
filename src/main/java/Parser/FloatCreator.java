package Parser;

import java.util.List;

/**
 * FloatCreator, a class which implements the creatorFromRow interface; through the create method,
 * it converts list of strings (row) to a list of floats.
 */

public class FloatCreator implements CreatorFromRow<List<Float>> {

  /**
   * create, a method that takes in a list of strings representing a row and produces a list of
   * floating point numbers. Create uses map to apply parseFloat to each element of the stream and
   * convert each to a floating-point number; toList() then converts the stream into a list after
   * applying map. Otherwise, a factoryFailureException is thrown.
   *
   * @param row, a list of strings generated from parsing a given row in a CSV file/data source
   * @return a list of floating point numbers, representing the floating point number translation of
   * the list of strings
   *
   * @throws FactoryFailureException, conveying that the conversion from list of strings to
   * a list of floating point numbers failed.
   */
  @Override
  public List<Float> create(List<String> row) throws FactoryFailureException {
    try {
      return (row.stream().map(Float::parseFloat)).toList();
    } catch (NumberFormatException e) {
      throw new FactoryFailureException(e.getMessage(), row);
    }
  }
}
