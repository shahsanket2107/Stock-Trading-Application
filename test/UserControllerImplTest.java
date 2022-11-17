import static org.junit.Assert.assertEquals;

import controller.UserController;
import controller.UserControllerImpl;
import model.UserImpl;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import view.ViewImpl;

/**
 * This is a test for UserControllerImpl. This class uses ByteArrayOutput Stream to test functions
 * as all the inputs were user-specified, and it is compared with Inputstream. This class computes
 * and asserts on actual value of model by passing in Inputstream object as if user were entering
 * values by running Main function.
 */
public class UserControllerImplTest {

  private PrintStream out;
  private ByteArrayOutputStream b;

  @Before
  public void setUp() {
    b = new ByteArrayOutputStream();
    out = new PrintStream(b);
  }

  private void outputMenu() {
    this.out.println();
    this.out.println("Enter 1 for making portfolio");
    this.out.println("Enter 2 to examine the composition of a particular portfolio");
    this.out.println("Enter 3 to determine the total value of portfolio on a certain date");
    this.out.println("Enter 4 to view all portfolio names");
    this.out.println("Enter 5 to load your portfolio");
    this.out.println("Enter 6 to create flexible portfolio");
    this.out.println("Enter 7 to examine composition of flexible portfolio");
    this.out.println("Enter 8 to load a flexible portfolio");
    this.out.println("Enter 9 to buy stocks on a specific date");
    this.out.println("Enter 10 to sell stocks on a specific date");
    this.out.println(
        "Enter 11 to determine the total value of flexible portfolio on a certain " + "date");
    this.out.println("Enter 12 to find cost basis of a flexible portfolio on a certain date");
    this.out.println(
        "Enter 13 to view how the portfolio has performed over a period of time using chart");
    this.out.println("Enter q to exit");
  }

  private void addStockToPortfolio() {
    this.out.println("Enter 1 to add stocks to your portfolio");
    this.out.println("Enter q to exit");
  }

  private void createPortfolioHelper() {
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: ");
    this.addStockToPortfolio();
  }



  @Test
  public void testMenu() {
    InputStream input = new ByteArrayInputStream("samved\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreatePortfolio() {
    InputStream input = new ByteArrayInputStream("samved\n1\np1\n1\naapl\n100\nq\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createPortfolioHelper();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreatePortfolioWithAlreadyExistingName() {
    InputStream input = new ByteArrayInputStream(
        "samved\n1\np1\n1\naapl\n100\nq\n1\np1\np2\n1\naapl\n100\nq\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createPortfolioHelper();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("The entered portfolio already exists. Try a different name!");
    this.out.println("Enter your portfolio name: ");
    this.addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: ");
    addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreatePortfolioWithInvalidTicker() {
    InputStream input = new ByteArrayInputStream(
        "samved\n1\np1\n1\nhehe\naapl\n100\nq\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: ");
    this.out.println("This ticker does not exist!! Please enter a valid ticker");
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: ");
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreatePortfolioWithInvalidQuantity() {
    InputStream input = new ByteArrayInputStream(
        "samved\n1\np1\n1\naapl\n1.2\n-3\n3\nq\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: ");
    this.out.println("Quantity should be an integer value!");
    this.out.println("Enter quantity of stocks: ");
    this.out.println("Quantity should be a positive value!");
    this.out.println("Enter quantity of stocks: ");
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCompositionOfPortfolio() {
    InputStream input = new ByteArrayInputStream(
        "samved\n1\np1\n1\naapl\n100\nq\n2\np1\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createPortfolioHelper();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Portfolio_Name: p1");
    this.out.println("{");
    this.out.println("\tStock_Ticker: AAPL");
    this.out.println("\tQuantity: 100");
    this.out.println("}");
    this.out.println();
    this.out.println();
    this.out.println();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testCompositionOfPortfolioWhoDoesNotExist() {
    InputStream input = new ByteArrayInputStream("samved\n2\np1\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("The given portfolio name does not exist!!");
    this.out.println("Please enter a valid portfolio name!!");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testTotalValueAtADate() {
    InputStream input = new ByteArrayInputStream(
        "samved\n1\np1\n1\naapl\n100\nq\n3\np1\n2022-10-20\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createPortfolioHelper();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println(
        "Portfolio_Name: p1\n" + "Portfolio_Valuation at 2022-10-20 is : $ 14338.999999999998\n"
            + "The stock valuation breakdown is: \n" + "AAPL : $14338.999999999998");
    this.out.println();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testTotalValueAtInvalidDate() {
    InputStream input = new ByteArrayInputStream(
        "samved\n1\np1\n1\naapl\n100\nq\n3\np1\n2023-10-20\n3\np1\n2020-10-10\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createPortfolioHelper();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("Date cannot be greater or equal to current date. Try a different date");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("Data for given parameter does not exist!!");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testViewAllPortfolios() {
    InputStream input = new ByteArrayInputStream(
        "samved\n1\np1\n1\naapl\n100\nq\n1\np2\n1\ngoogl\n50\nq\n4\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createPortfolioHelper();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: \n"
        + "Enter quantity of stocks: ");
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("The list of portfolios is:\n" + "p1\n" + "p2\n");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testViewAllPortfoliosIfNoneExist() {
    InputStream input = new ByteArrayInputStream("samved\n4\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("No portfolios exist at this time!! ");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testLoadPortfolio() {
    InputStream input = new ByteArrayInputStream(
        "samved\n5\nsamved_portfolios.xml\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your file name (with extension): ");
    this.out.println("Portfolio loaded successfully!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testLoadPortfolioWIthInvalidPath() {
    InputStream input = new ByteArrayInputStream("samved\n5\nhahaha.txt\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your file name (with extension): ");
    this.out.println("Invalid file name. Please try again!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testLoadAndThenCheckComposition() {
    InputStream input = new ByteArrayInputStream(
        "samved\n5\nsamved_portfolios.xml\n2\np1\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your file name (with extension): ");
    this.out.println("Portfolio loaded successfully!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Portfolio_Name: p1");
    this.out.println("{");
    this.out.println("\tStock_Ticker: GOOGL");
    this.out.println("\tQuantity: 30");
    this.out.println("}");
    this.out.println("{");
    this.out.println("\tStock_Ticker: AAPL");
    this.out.println("\tQuantity: 2");
    this.out.println("}");
    this.out.println();
    this.out.println();
    this.out.println();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }



}