package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.*;

import Parser.FactoryFailureException;
import Parser.FloatCreator;
import Parser.Parser;
import Parser.TrivialCreator;
import java.io.*;
import java.util.List;
import org.junit.jupiter.api.Test;
import Parser.SkipHeaderCreator;

/**
 * functionalities
 *
 * <p>Tests for the parser class
 */

public class ParserTest {

  Parser<List<String>> incomeByRaceParser;
  Parser<List<String>> malformedParser;
  Parser<List<String>> quoteMishapParser;
  Parser<List<String>> habitatParser;

  // test parsing uniform CSV
  @Test
  public void testParseRegCSV() {
    try {
      incomeByRaceParser =
          new Parser<List<String>>("data/census/income_by_race.csv", new TrivialCreator());
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
    assertFalse(
        incomeByRaceParser.getParsedContent().contains(List.of("Gemini", "Roberto", "Nick")));
  }

  // test parsing malformed data
  @Test
  public void testParseMalformedCSV() {
    try {
      malformedParser =
          new Parser<List<String>>("data/malformed/malformed_signs.csv", new TrivialCreator());
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
        assertThrows(
            FileNotFoundException.class,
            () -> new Parser<List<String>>("data/census/housing.csv", new TrivialCreator()));
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
      //        assertEquals(List.of("Gemini", "Roberto", "Nick"),
      // quoteMishapParser.getParsedContent().get(3));

    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }
  }
  // test: normal behavior on inputs that exercise different functionality (e.g., CSV data with and
  // without column headers)

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

    // this assertEquals fails. Should it fail & count all 4 empty spaces if there are only 2
    // entries?
    assertEquals(4, habitatParser.getParsedContent().get(2).size());

    assertEquals(2, habitatParser.getParsedContent().get(4).size());
    assertEquals(4, habitatParser.getParsedContent().get(0).size());
    assertEquals(
        List.of(
            // coniferous, lakes, plants, grasslands
            "coniferous", "lakes", "plants", "grasslands"),
        habitatParser.getParsedContent().get(0));
  }

  /**
   * Testing for trailing comma
   */
  @Test
  public void testParseBirdSizeOfTrailingComma() {
    try {
      habitatParser = new Parser<List<String>>("data/birds/habitat.csv", new TrivialCreator());
      habitatParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }
    assertEquals(2, habitatParser.getParsedContent().get(4).size());
  }

  /**
   * Testing size for Bird/habitat data
   */
  @Test
  public void testParseBirdSizeOfStandardLine() {
    try {
      habitatParser = new Parser<List<String>>("data/birds/habitat.csv", new TrivialCreator());
      habitatParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }
    assertEquals(4, habitatParser.getParsedContent().get(0).size());
  }

  /**
   * testing to ensure that the right contents were in the CSV file
   */
  @Test
  public void testParseContainsHabitats() {
    try {
      habitatParser = new Parser<List<String>>("data/birds/habitat.csv", new TrivialCreator());
      habitatParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }
    assertEquals(
        List.of(
            // coniferous, lakes, plants, grasslands
            "coniferous", "lakes", "plants", "grasslands"),
        habitatParser.getParsedContent().get(0));
  }

  // test: CSV data in different reader types (e.g. StringReader and FileReader)
  @Test
  public void stringReaderTest() {
    String stringReaderWords = "Magnanimous,esoteric,ebullient,axiom";
    Parser<List<String>> eclecticParser;
    try {
      eclecticParser =
          new Parser<List<String>>(new StringReader(stringReaderWords), new TrivialCreator());
      eclecticParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }
    assertEquals(
        List.of(
            // magnanimous, esoteric, ebullient, axiom
            "Magnanimous", "esoteric", "ebullient", "axiom"),
        eclecticParser.getParsedContent().get(0));
  }

  /**
   * testing factory failure exception
   */
  @Test
  public void factoryFailureExceptionTest() {
    String stringReaderWords = "Magnanimous,esoteric,ebullient,axiom";
    Parser<List<Float>> eclecticParser;

    eclecticParser =
        new Parser<List<Float>>(new StringReader(stringReaderWords), new FloatCreator());
    assertThrows(FactoryFailureException.class, eclecticParser::parse);
  }

  /**
   * testing that float creator works
   * @throws IOException
   * @throws FactoryFailureException
   */

  @Test
  public void floatCreatorTest() throws IOException, FactoryFailureException {
    String string125 = "1.25";
    Parser<List<Float>> arbitraryFloat;
    arbitraryFloat = new Parser<List<Float>>(new StringReader(string125), new FloatCreator());
    arbitraryFloat.parse();
    assertEquals(
        List.of(
            // magnanimous, esoteric, ebullient, axiom
            1.25f),
        arbitraryFloat.getParsedContent().get(0));
  }

  /**
   * testing to ensure that skipHeader class works
   */
  @Test
  public void skipHeaderTest() {
    try {
      habitatParser = new Parser<List<String>>("data/birds/habitat.csv", new SkipHeaderCreator());
      habitatParser.parse();
    } catch (IOException | FactoryFailureException e) {
      throw new RuntimeException(e);
    }
    assertEquals(
        List.of(),
        habitatParser.getParsedContent().get(0));
    assertEquals(
        List.of("deciduous", "ponds", "bushes", "agricultural fields"),
        habitatParser.getParsedContent().get(1));
  }

}
  // CSV data when reader itself throws an exception
  // Q: are these IOException, FactoryFailureException exceptions or something else?

