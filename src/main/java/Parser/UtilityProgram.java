package Parser;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UtilityProgram {

  /**
   * filename, denoting the name of a file
   */
  private String fileName;

  /**
   * the value (a String) to search for
   * @param <T>
   */
  private String value;

  /**
   * a column indexer
   * @param <T>
   */

  private int columnIndex;

  /**
   * column name: searches column by name
   * @param <T>
   */

  private String columnName;

  /**
   * column identifier
   */
  private ColumnIdentifier columnIdentifier;

  /**
   * Parser
   */

  private Parser<List<String>> parser;

  /**
   * constructor
   * @param <T>
   */

  public enum ColumnIdentifier{
    COLUMN_INDEX, COLUMN_NAME, ALL_COLUMNS
  }

  // have three constructors instead of pattern matching
  public UtilityProgram(String fileName, String value, int columnIndex, String columnName, ColumnIdentifier columnIdentifier)
      throws IOException, FactoryFailureException {
    this.fileName = fileName;
    this.value = value;
    this.columnIndex = columnIndex;
    this.columnName = columnName;
    this.parser = new Parser<List<String>>(fileName,new TrivialCreator());
    this.parser.parse();
    this.columnIdentifier = columnIdentifier;
  }

  public UtilityProgram(String fileName, String value, int columnIndex)
      throws IOException, FactoryFailureException {
    this(fileName, value, columnIndex,  "", ColumnIdentifier.COLUMN_INDEX);
  }

  public UtilityProgram(String fileName, String value, String columnName)
      throws IOException, FactoryFailureException {
    // refactor to have none ?
    this(fileName, value, 0, columnName, ColumnIdentifier.COLUMN_NAME);
  }

  // NONE?
  public UtilityProgram(String fileName, String value)
      throws IOException, FactoryFailureException {
    this(fileName, value, 0, "", ColumnIdentifier.ALL_COLUMNS);
  }

  public List<List<String>> dataSearcher() {
    List<List<String>> parsedContent = this.parser.getParsedContent();
    List<List<String>> matchingContent = new ArrayList<>() ;
    for (List<String> row : parsedContent) {
      switch(this.columnIdentifier) {
        case COLUMN_INDEX:
          if (row.get(columnIndex).equals(value)) {
            matchingContent.add(row);
          }
        case COLUMN_NAME:
          /* TODO: get a list of headers and search for that
           */
          continue;
        case ALL_COLUMNS:
          if (row.contains(value)) {
            matchingContent.add(row);
          }
      }

    }
    return matchingContent;
  }


}


