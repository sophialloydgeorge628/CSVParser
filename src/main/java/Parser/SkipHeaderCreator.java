package Parser;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

// skipping header for a given CSV file
public class SkipHeaderCreator implements CreatorFromRow<List<String>> {

  boolean state;

  public SkipHeaderCreator() {
    this.state = true;
  }

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



