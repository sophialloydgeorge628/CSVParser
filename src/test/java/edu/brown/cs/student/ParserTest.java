package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import Parser.Parser;
import java.io.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import Parser.TrivialCreator;
import Parser.FactoryFailureException;

/**
 * TODO: add more tests in this file to build an extensive test suite for your parser and parsing
 * functionalities
 *
 * <p>Tests for the parser class
 */

/**
 * Some ideas for tests I might want to write:
 * 1) tests for CSV files with more than one comma
 * 2) What if we don't know that the first line is meaningful? (e.g., in ri earnings disparity,
 * that seemed to be the case?)
 * 3) what about total? What if the lines represent different things? How to parse it?
 */
public class ParserTest {
  Parser<List<String>> incomeByRaceParser;
  Parser<List<String>> malformedParser;
  Parser<List<String>> quoteMishapParser;
  Parser<List<String>> habitatParser;

  // test parsing uniformed CSV
  @Test
  public void testParseRegCSV() {
    try {
      incomeByRaceParser = new Parser<List<String>>("data/census/income_by_race.csv", new TrivialCreator());
      incomeByRaceParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }

    assertEquals(324, incomeByRaceParser.getParsedContent().size());
    assertEquals(9, incomeByRaceParser.getParsedContent().get(223).size());
    assertEquals(9, incomeByRaceParser.getParsedContent().get(0).size());
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
        incomeByRaceParser.getParsedContent().get(143));
    assertFalse(incomeByRaceParser.getParsedContent().contains(List.of("Gemini", "Roberto", "Nick")));
  }

  // test parsing malformed data
  @Test
  public void testParseMalformedCSV() {
    try {
      malformedParser = new Parser<List<String>>("data/malformed/malformed_signs.csv", new TrivialCreator());
      malformedParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }

    assertEquals(13, malformedParser.getParsedContent().size());
    assertEquals(2, malformedParser.getParsedContent().get(0).size());
    assertEquals(List.of("Aquarius"), malformedParser.getParsedContent().get(11));
    assertEquals(List.of("Gemini", "Roberto", "Nick"), malformedParser.getParsedContent().get(3));
  }

  // test parser for a file not found, example for exception testing
  @Test
  public void testFileNotFoundParse() throws IOException {
    Exception exception =
        assertThrows(FileNotFoundException.class, () -> new Parser<List<String>>("data/census/housing.csv", new TrivialCreator()));
  }

  @Test
  // test to see if parsing is successful given two sequential commas
  public void testCommaQuoteParse() {
    try {
       quoteMishapParser = new Parser<List<String>>("data/stars/ten-star.csv", new TrivialCreator());
       quoteMishapParser.parse();
        assertEquals(11, quoteMishapParser.getParsedContent().size());
        assertEquals(5, quoteMishapParser.getParsedContent().get(0).size());
        assertEquals("", quoteMishapParser.getParsedContent().get(10).get(1));
//        assertEquals(List.of("Gemini", "Roberto", "Nick"), quoteMishapParser.getParsedContent().get(3));

    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }

  }
  // test: normal behavior on inputs that exercise different functionality (e.g., CSV data with and without column headers)

  // test for parsing success given no column headers
  @Test
  public void testParseBirdNoColumnHeaderCSV() {
    try {
      habitatParser = new Parser<List<String>>("data/birds/habitat.csv", new TrivialCreator());
      habitatParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }

    assertEquals(5, habitatParser.getParsedContent().size());

    // this assertEquals fails. Should it fail & count all 4 empty spaces if there are only 2 entries?
    assertEquals(2, habitatParser.getParsedContent().get(2).size());
    assertEquals(1, habitatParser.getParsedContent().get(4).size());
    assertEquals(4, habitatParser.getParsedContent().get(0).size());
    assertEquals(
        List.of(
            // coniferous, lakes, plants, grasslands
            "coniferous",
            "lakes",
            "plants",
            "grasslands"),
        habitatParser.getParsedContent().get(0));
    assertFalse(habitatParser.getParsedContent().contains(List.of("Sparrow", "Scrub Jay", "Owl")));
  }

  // test: CSV data in different reader types (e.g. StringReader and FileReader)
  // I suppose we are currently using file reader bc CSV files are files? or string reader because we're parsing those
  // files?
  // right now, I think we're just testing for String reader because we are testing the case where
  // we have a path to the file?
  // but how can we do something else? How do we pass in a reader?

  // test: CSV data with inconsistent column count (what does this mean?)

  // CSV data when reader itself throws an exception
  // Q: are these IOException, FactoryFailureException exceptions or something else?
}
