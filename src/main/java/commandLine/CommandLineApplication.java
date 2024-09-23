package commandLine;

// parse input, call searchProgram
import searcher.CSVSearcher;
import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;
import parser.FactoryFailureException;
import java.util.zip.DataFormatException;

/**
 * Citation (Class Livecode): https://github.com/cs0320/class-livecode
 */

/**
 * A command line application class that integrates a scanner, which is a java class that is used
 * for reading input
 *
 * Citation: https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html
 *
 */
public class CommandLineApplication {
  private final InputStream in;
  private final PrintStream out;
  private final PrintStream err;
  private final Scanner scan;

  /**
   * Create a CommandProcessor that listens for input, sends output, and sends errors to the given streams.
   * @param in the input stream to use
   * @param out the output stream to use
   * @param err the error stream to use
   *   I also construct a new scanner, which does not need to be passed into the command line
   *            application
   */

  public CommandLineApplication(InputStream in, PrintStream out, PrintStream err) {
    this.in = in;
    this.out = out;
    this.err = err;
    this.scan = new Scanner(this.in);
  }

  /**
   * Create a CommandProcessor using the standard System-defined input, output, and error streams.
   */
  public CommandLineApplication() {
    this(System.in, System.out, System.err);
  }

  /**
   * method to prompt the user to enter in a correct value. This method takes
   *
   * want to check until their input matches isValid
   *
   * predicate string is a function in which you enter strings and returns yes or no
   */

  /**
   * CommandLinePrompter takes in a
   * @param userMessage, a string that represents the user message
   * @param isValid, a predicate that determines whether to string is valid, returning true if so
   *                 and false otherwise
   * @return a string, representing the valid input checked against the isValid predicate
   */

  public String commandLinePrompter(String userMessage, Predicate<String> isValid){
        this.out.print(userMessage + ": ");

        String nextInput = scan.nextLine();

        // while next input isn't valid
        while (!isValid.test(nextInput)) {
            this.out.println("Please try again");
            nextInput = scan.nextLine();
        }
        return nextInput;
  }


  // CSVsearch

  // display basic screen, press "s" key to start, "q" to quit
  // display 1st question: "Please enter the name of a CSV data source to parse."
  // ... if string entered, proceed. Else: print error message and prompt the user again
  // ... if file not found, then print error message and return to question

  // display 3rd question (if y): "Please enter the value for which you'd like to search"
  // if string, proceed. Else: print error message & return to question

  // display 4th question: "Would you like to narrow your search by searching only within a specific
  // column index or column header? Please enter y or n"

  // if yes --> display 5th question (if y): "Would you like to search via column index or column header? Enter
  // i for index or h for header. Note: you can only use one of these parameters to search"

  // .... if user enters both parameters, print informative error message and ask the question again

  // if i --> "Please enter the column index through which you'd like to search as an integer" (if no ,
  // integer is entered, print error statement and ask user to enter an integer)

  // if h --> "Please enter the header through which you'd like search" (phrasing could be improved)
  // ... input must be a string; if it is not, print error message and go back to question

  // if n --> run utility program for the no columnIndex and no headers case (search the entire CSV)
  // if y --> run utility program for the specified column index or header

  // if match found, print message "Your match was found: .... print rows where match was found"
  // otherwise, print "Your match was not found."

  // "Would you like to search through another CSV data source or change your existing search value? Press y for yes or
  // n for no." n will cause program to quit; y will go through program again

  // GENERAL QUESTIONS: 1) what does "matching" mean for us? I think it means exact matching if we
  // are using .contains() & .equals()?
  //
  // 2) need to incorporate "file cannot be found" error if the CSV data
  // source is not found

  // Citation: https://docs.oracle.com/javase/8/docs/api/java/util/Scanner.html

  /**
   * run method, which runs the command line processor through user messages. I make use of lambdas
   * for each prompt
   */

  public void run() {
    // This is a "try with resources"; the resource will automatically
    // be closed if necessary. Prefer this over finally blocks.
    // See: https://docs.oracle.com/javase/tutorial/essential/exceptions/tryResourceClose.html

    // The first user message is displayed; the user must either quit the program or proceed
    try {
      String userInput = this.commandLinePrompter("Please enter s to start or q to quit",
          m -> m.equals("s") || m.equals("q"));
      if (userInput.equals("q")) {
        this.out.println("Quitting CSVSearch");
        return;
      }
      // this will return true, and then we have a catch later on for fileNotFound exception
      String csvDataSource = this.commandLinePrompter(
          "Please enter the name of a CSV datasource to parse", m -> true);

      // CSV search value: same logic applies here
      String csvSearchValue = this.commandLinePrompter(
          "Please enter the value for which you'd like to search", m -> true);

      // CSV narrowSearch: this enables the user to narrow their search by specifying a column index
      // or column header
      String narrowSearch = this.commandLinePrompter("Would you like to narrow your "
              + "search by searching only within a specific column index or column header? Please enter y or n",
          m -> m.equals("y") || m.equals("n"));
      CSVSearcher program;
      if (narrowSearch.equals("y")) {
        String specifiedSearch = this.commandLinePrompter(
            "Would you like to search via column index or column header? "
                + "Enter i for index or h for header. Note: you can only use one of these parameters to search",
            m -> m.equals("h") || m.equals("i"));
        if (specifiedSearch.equals("h")) {
          String headerString = this.commandLinePrompter(
              "Please enter the header through which you'd like search",
              m -> true);
          program = new CSVSearcher(csvDataSource, csvSearchValue, headerString);
        }

        // case for headerIndex
        // citation: https://stackoverflow.com/questions/5439529/determine-if-a-string-is-an-integer-in-java
        // the regex was used to determine whether the column index is valid
        else {
          String headerString = this.commandLinePrompter(
              "Please enter the column index through which you'd like search",
              m -> m.matches("^(0|[1-9]\\d*)$"));
          program = new CSVSearcher(csvDataSource, csvSearchValue,
              Integer.parseInt(headerString));
        }
      } else {
        program = new CSVSearcher(csvDataSource, csvSearchValue);
      }

      // use dataSearcher to generate a list of matches

      List<List<String>> matches = program.dataSearcher();

      if (matches.isEmpty()) {
        this.out.println("Your match was not found.");
      } else {
        this.out.println("Here are the rows in which your match was found: ");
        for (List<String> row : matches) {
          this.out.println(String.join(",", row));
        }
      }

      // Error Handling

      // file couldn't be found or read
    } catch (IOException e) {
      this.err.println(e);
      // parser from the rows would cause a factory failure exception; we're using trivial creator
    } catch (FactoryFailureException e) {
      this.err.println(e);

    } catch (DataFormatException e) {
      this.err.println("Header not found error.");

    } catch (IndexOutOfBoundsException e) {
      this.err.println("Column out of bounds error" + " " + e);
    }
  }

  /**
   * This is the entry point for the command-line application.
   */
  public static void main(String[] args) {
    CommandLineApplication proc = new CommandLineApplication();
    proc.run();
  }
}