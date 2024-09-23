package edu.brown.cs.student;

import static java.lang.System.err;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import commandLine.CommandLineApplication;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.io.StringBufferInputStream;
import java.io.StringReader;
import java.util.List;
import org.junit.jupiter.api.Test;
import parser.FactoryFailureException;
import parser.Parser;
import parser.TrivialCreator;
import java.io.InputStream;

//TODO: wrap in a method, pass in s data birds shorelines etc.

// when pass in header name, don't return first row as a match
// test that it's included or isn't

public class CommandLineApplicationTest {

  // google input stream java

  // https://stackoverflow.com/questions/62241/how-to-convert-a-reader-to-inputstream-and-a-writer-to-outputstream

  // https://docs.oracle.com/javase/8/docs/api/java/io/OutputStream.html
  @Test
  public void testCommandLineHeaderError(){
    // making mock objects
    InputStream mockIn = new ByteArrayInputStream(
        "s\ndata/birds/habitat.csv\nshorelines\ny\n1\nh\n1".getBytes());
    ByteArrayOutputStream errorStream = new ByteArrayOutputStream();
    ByteArrayOutputStream outStream = new ByteArrayOutputStream();
    PrintStream mockOut = new PrintStream(outStream);
    PrintStream mockError = new PrintStream(errorStream);
    CommandLineApplication app = new CommandLineApplication(mockIn, mockOut, mockError);
    app.run();
    // alternative string
    assertEquals("Header not found error.\n", errorStream.toString());
  }



}
