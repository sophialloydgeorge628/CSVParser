package edu.brown.cs.student;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import Parser.Parser;
import Parser.UtilityProgram.ColumnIdentifier;
import java.io.IOException;
import java.util.List;
import org.junit.jupiter.api.Test;
import Parser.UtilityProgram;
import Parser.FactoryFailureException;

public class UtilityTest {


  @Test
  public void testUtilityProgram(){
    try {
      UtilityProgram utility = new UtilityProgram("data/stars/stardata.csv", "Eura");
      assertEquals(
          List.of(List.of(
              "8","Eura","174.01562","0.08288","84.44669")),
         utility.dataSearcher());

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (FactoryFailureException e) {
      throw new RuntimeException(e);
    }
  }
}
