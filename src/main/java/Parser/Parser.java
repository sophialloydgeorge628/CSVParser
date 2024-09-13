package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

// added <T> to parser
public class Parser<T> {
  /** TODO is this defensive enough? Feel free to edit any variable declarations */
  private Reader reader;

  private List<T> parsedContent;

  private CreatorFromRow<T> creator;

  /**
   * TODO feel free to modify the header and body of this constructor however you wish
   *
   * @param filePath - path to a csv file to be parsed
   */

  public Parser(String filePath, CreatorFromRow<T> creator) throws IOException {
      this(new FileReader(filePath), creator);
  }

  /**
   * Maybe cite an LLM here? function with the same name that has two different signatures
   * test for the case where you end up with a StringReader
   * https://stackoverflow.com/questions/285177/how-do-i-call-one-constructor-from-another-in-java
   */

  /**
   * Parsing a reader
   * @param reader, where reader is a generic
   */

  public Parser(Reader reader, CreatorFromRow<T> creator){
    this.reader = reader;
    this.parsedContent = new ArrayList<>();
    this.creator = creator;
  }

  /**
   * getParsedContent: a method to return parsedContent
   */

  public List<T> getParsedContent() {
    return parsedContent;
  }


  /**
   * TODO feel free to modify this method to incorporate your design choices
   *
   * @throws IOException when buffer reader fails to read-in a line
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

