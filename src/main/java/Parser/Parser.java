package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Parser: a class that parses CSV data sources
 * @param <T> a generic type, used in parsedContent to ensure generalizability
 */
// added <T> to parser
public class Parser<T> {
  /**
   * reader represents an abstract class and enables its caller to read from a data source
   */
  private Reader reader;

  /**
   * parsedContent is a list of a generic type resulting from parsing a data source
   */
  private List<T> parsedContent;

  /**
   * CreatorFromRow is an interface that
   */
  private CreatorFromRow<T> creator;

  /**
   * A Parser Constructor, given a filePath and a CreatorFromRow creator
   * @param filePath - path to a csv file to be parsed
   * @param creator - a creatorFromRow object, which determines how the CSV data will be parsed
   * @throws IOException - an exception that indicates an error in performing input / output
   * operations
   *
   * Constructor Chaining in Java
   * Reference: <a href="https://stackoverflow.com/questions/285177/how-do-i-call-one-constructor-from-another-in-java">...</a>
   */

  public Parser(String filePath, CreatorFromRow<T> creator) throws IOException {
    this(new FileReader(filePath), creator);
  }

  /**
   * Parser Constructor, given a reader and a creator
   *
   * @param reader, an abstract class in which a reader object allows its reader to call from
   *                a number of data sources (including FileReader and StringReader)
   */
  public Parser(Reader reader, CreatorFromRow<T> creator) {
    this.reader = reader;
    this.parsedContent = new ArrayList<>();
    this.creator = creator;
  }

  /** getParsedContent: a getter method to return the parsed content of a file of a generic type  */
  public List<T> getParsedContent() {
    return parsedContent;
  }

  /**
   * parse, a method which specifies how to parse from a CSV data source according to a
   * regex specification. The content of the CSV is added to a list of strings, representing
   * the parsed content, after being split into an array per the regex pattern.
   * @throws IOException , an input-output exception that occurs if there are errors with the input
   * or output
   * @throws FactoryFailureException , an exception that would be thrown if there were an error in
   * creating a row of parsed content
   */
  public void parse() throws IOException, FactoryFailureException {
    String line;
    Pattern regexSplitCSVRow = Pattern.compile(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))");
    BufferedReader readInBuffer =
        new BufferedReader(reader); // wraps around readers to improve efficiency when reading

    while ((line = readInBuffer.readLine()) != null) {
      String[] result = regexSplitCSVRow.split(line);
      List<String> lineToArr = Arrays.stream(result).toList();
      parsedContent.add(this.creator.create(lineToArr));
    }
    readInBuffer.close();
  }
}
