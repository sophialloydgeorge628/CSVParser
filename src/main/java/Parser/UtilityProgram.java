package Parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UtilityProgram {

  /**
   * filename, denoting the name of a file
   */
  private final String fileName;

  /**
   * the value (a String) to search for
   *
   * @param <T>
   */
  private final String value;

  /**
   * a column indexer
   *
   * @param <T>
   */

  private final Optional<Integer> columnIndex;

  /**
   * column name: searches column by name
   *
   * @param <T>
   */

  private final Optional<String> columnName;

  /**
   * Parser
   */

  private final Parser<List<String>> parser;


  // have three constructors instead of pattern matching
  private UtilityProgram(String fileName, String value, Optional<Integer> columnIndex,
      Optional<String> columnName)
      throws IOException, FactoryFailureException {
    this.fileName = fileName;
    this.value = value;
    this.columnIndex = columnIndex;
    this.columnName = columnName;
    this.parser = new Parser<List<String>>(fileName, new TrivialCreator());
    this.parser.parse();
  }

  // column index

  public UtilityProgram(String fileName, String value, int columnIndex)
      throws IOException, FactoryFailureException {
    this(fileName, value, Optional.of(columnIndex), Optional.empty());
  }

  public UtilityProgram(String fileName, String value, String columnName)
      throws IOException, FactoryFailureException {
    // refactor to have none ?
    this(fileName, value, Optional.empty(), Optional.of(columnName));
  }

  // NONE?
  public UtilityProgram(String fileName, String value)
      throws IOException, FactoryFailureException {
    this(fileName, value, Optional.empty(), Optional.empty());
  }

  public int stringToIndexColumn(List<List<String>> parsedContent, String columnHeader){
    // no guarantee that your CSV actually has a column row; could be an empty file
    List<String> headerRow = parsedContent.get(0);
    int headerIndex = headerRow.indexOf(columnHeader);
    if (headerIndex == -1){
          throw new IndexOutOfBoundsException();
      }
    else {
      return headerIndex;
    }
  }

  public List<List<String>> dataSearcher() {
    List<List<String>> parsedContent = this.parser.getParsedContent();
    List<List<String>> matchingContent = new ArrayList<>();
    // two cases: after we check for the two optional case
    // .orElse method of optional
    // if we map a function that turns a string into an integer, we will get an optional integer
    // otherwise, we don't call that function
    // then we have two optional integers (columnIndex which was passed in by the user)

    // case in which both index & name are present
    if (columnIndex.isPresent() && columnName.isPresent()) {
      throw new RuntimeException();
    }

    // index of column that corresponds to string column name if given
    Optional<Integer> nameIndex = columnName.map(s -> this.stringToIndexColumn(parsedContent, s));

    // searching through rows, need to know matching: 1) both cases; 2) string "TOWN"; 3) or they give you a num; 4) null
    // both name * index, name but no index, index but no name, both are null (match all of the columns)

    // common index
    Optional<Integer> commonIndex = columnIndex.or(() -> nameIndex);

    for (List<String> row : parsedContent) {
      commonIndex.ifPresent(i -> {
            if (
                row.get(i).equals(value)) {
              matchingContent.add(row);
            }
          }
      );

      if (commonIndex.isEmpty()) {
        if (row.contains(value)) {
          matchingContent.add(row);
        }
      }
    }
    return matchingContent;
  }
}


