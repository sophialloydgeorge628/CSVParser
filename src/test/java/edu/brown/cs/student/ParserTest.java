package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import Parser.Parser;
import java.io.*;
import java.util.List;
import org.junit.jupiter.api.Test;

/**
 * TODO: add more tests in this file to build an extensive test suite for your parser and parsing
 * functionalities
 *
 * <p>Tests for the parser class
 */
public class ParserTest {
  Parser incomeByRaceParser;
  Parser malformedParser;

  // test parsing uniformed CSV
  @Test
  public void testParseRegCSV() {
    try {
      incomeByRaceParser = new Parser("data/census/income_by_race.csv");
      incomeByRaceParser.parse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(324, incomeByRaceParser.parsedContent.size());
    assertEquals(9, incomeByRaceParser.parsedContent.get(223).size());
    assertEquals(9, incomeByRaceParser.parsedContent.get(0).size());
    assertEquals(
        List.of(
            "7",
            "Two Or More",
            "2017",
            "2017",
            "44000",
            "11831",
            "\"Kent County, RI\"",
            "05000US44003",
            "kent-county-ri"),
        incomeByRaceParser.parsedContent.get(143));
    assertFalse(incomeByRaceParser.parsedContent.contains(List.of("Gemini", "Roberto", "Nick")));
  }

  // test parsing malformed data
  @Test
  public void testParseMalformedCSV() {
    try {
      malformedParser = new Parser("data/malformed/malformed_signs.csv");
      malformedParser.parse();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertEquals(13, malformedParser.parsedContent.size());
    assertEquals(2, malformedParser.parsedContent.get(0).size());
    assertEquals(List.of("Aquarius"), malformedParser.parsedContent.get(11));
    assertEquals(List.of("Gemini", "Roberto", "Nick"), malformedParser.parsedContent.get(3));
  }

  // test parser for a file not found, example for exception testing
  @Test
  public void testFileNotFoundParse() throws IOException {
    Exception exception =
        assertThrows(FileNotFoundException.class, () -> new Parser("data/census/housing.csv"));
  }
}
