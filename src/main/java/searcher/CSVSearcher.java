package searcher;

import parser.Parser;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.zip.DataFormatException;
import parser.FactoryFailureException;
import parser.TrivialCreator;

/**
 * CSVSearcher, a program which renders a CSV datasource search more specific
 */

public class CSVSearcher {

  /**
   * filename, denoting the name of a file
   */
  private final String fileName;

  /**
   * the value (a String) to search for
   */

  private final String value;

  /**
   * a column indexer
   *
   * @param <T> , which represents a specified column index through which to search
   */

  private final Optional<Integer> columnIndex;

  /**
   * column name: searches column by name
   *
   * @param <T> String, which will be a string; otherwise, the optional will return nothing
   */

  private final Optional<String> columnName;

  /**
   * Parser, representing the list of parsed content
   */

  private final Parser<List<String>> parser;


  // have three constructors instead of pattern matching
  // used trivial creator

  /**
   * Constructor for CSV searcher
   * @param fileName, the parsed CSV datasource
   * @param value, the string for which to search
   * @param columnIndex, the index to which to narrow the search
   * @param columnName, the column through which to narrow the search
   * @throws IOException, an input output exception, indicating an issue with reading from a file,
   * etc.
   * @throws FactoryFailureException, an exception suggesting an issue with initializing objects,
   *
   */
  private CSVSearcher(String fileName, String value, Optional<Integer> columnIndex,
      Optional<String> columnName)
      throws IOException, FactoryFailureException {
    this.fileName = fileName;
    this.value = value;
    this.columnIndex = columnIndex;
    this.columnName = columnName;
    this.parser = new Parser<List<String>>(fileName, new TrivialCreator());
    this.parser.parse();
  }

  // column index, which specifies a column through which to search
  public CSVSearcher(String fileName, String value, int columnIndex)
      throws IOException, FactoryFailureException {
    this(fileName, value, Optional.of(columnIndex), Optional.empty());
  }

  // case in which we have a columnName given to us
  public CSVSearcher(String fileName, String value, String columnName)
      throws IOException, FactoryFailureException {

    this(fileName, value, Optional.empty(), Optional.of(columnName));
  }

  // CSV searcher, which represents the case in which no narrowing down of the search
  public CSVSearcher(String fileName, String value)
      throws IOException, FactoryFailureException {
    this(fileName, value, Optional.empty(), Optional.empty());
  }

  // throwing an index out of bounds exception if index == -1, which indicates that the index is -1
  public int stringToIndexColumn(List<List<String>> parsedContent, String columnHeader) throws DataFormatException {
    // no guarantee that CSV actually has a column row; could be an empty file
    List<String> headerRow = parsedContent.get(0);
    int headerIndex = headerRow.indexOf(columnHeader);
    if (headerIndex == -1){
          throw new DataFormatException();
      }
    else {
      return headerIndex;
    }
  }
   // index out of bounds would happen if row has 10 columns and you're out of bounds
  public List<List<String>> dataSearcher() throws DataFormatException, IndexOutOfBoundsException {
    List<List<String>> parsedContent = this.parser.getParsedContent();
    List<List<String>> matchingContent = new ArrayList<>();
    // two cases: after we check for the two optional case
    // .orElse method of optional
    // if we map a function that turns a string into an integer, we will get an optional integer
    // otherwise, we don't call that function
    // then we have two optional integers (columnIndex which was passed in by the user)

    // case in which both index & name are present: this case shouldn't occur
    if (columnIndex.isPresent() && columnName.isPresent()) {
      throw new RuntimeException();
    }

    // index of column that corresponds to string column name if given
    Optional<Integer> nameIndex;
    try {
       nameIndex = columnName.map(s -> {
        try {
          return this.stringToIndexColumn(parsedContent, s);
        } catch (DataFormatException e) {
          throw new RuntimeException(e);
        }

      });
    }
    // header wasn't found
    catch (RuntimeException e){
      throw new DataFormatException();
    }

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


