package Parser;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class Parser {
  /** TODO is this defensive enough? Feel free to edit any variable declarations */
  public FileReader reader;

  public List<List<String>> parsedContent;

  /**
   * TODO feel free to modify the header and body of this constructor however you wish
   *
   * @param filePath - path to a csv file to be parsed
   */
  public Parser(String filePath) throws IOException {
    this.reader = new FileReader(filePath);
    this.parsedContent = new ArrayList<>();
  }

  /**
   * TODO feel free to modify this method to incorporate your design choices
   *
   * @throws IOException when buffer reader fails to read-in a line
   */
  public void parse() throws IOException {
    String line;
    Pattern regexSplitCSVRow = Pattern.compile(",(?=([^\\\"]*\\\"[^\\\"]*\\\")*(?![^\\\"]*\\\"))");
    BufferedReader readInBuffer =
        new BufferedReader(reader); // wraps around readers to improve efficiency when reading

    while ((line = readInBuffer.readLine()) != null) {
      String[] result = regexSplitCSVRow.split(line);
      List<String> lineToArr = Arrays.stream(result).toList();
      parsedContent.add(lineToArr);
    }
    readInBuffer.close();
  }
}
