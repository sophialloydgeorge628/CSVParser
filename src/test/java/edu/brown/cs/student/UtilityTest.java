package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.zip.DataFormatException;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import searcher.CSVSearcher;
import parser.FactoryFailureException;

public class UtilityTest {


  @Test
  public void testUtilityProgram(){
    try {
      CSVSearcher utility = new CSVSearcher("data/stars/stardata.csv", "Eura");
      assertEquals(
          List.of(List.of(
              "8","Eura","174.01562","0.08288","84.44669")),
         utility.dataSearcher());

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (FactoryFailureException | DataFormatException e) {
      throw new RuntimeException(e);
    }
  }
}

// Normal Behavior:

// searching for values that are, and aren't, present in the CSV

// searching for values that are present, but are in the wrong column

// searching for values by index

// searching for values by column name

// searching for values without a column identifier

//
