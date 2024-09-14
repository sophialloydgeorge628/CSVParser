package Parser;

import java.util.List;

public class FloatCreator implements CreatorFromRow<List<Float>> {

  @Override
  public List<Float> create(List<String> row) throws FactoryFailureException {
    try {
      return (row.stream().map(Float::parseFloat)).toList();
    } catch (NumberFormatException e) {
      throw new FactoryFailureException(e.getMessage(), row);
    }
  }
}
