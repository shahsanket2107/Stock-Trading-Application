import static org.junit.Assert.assertEquals;

import controller.UserController;
import controller.UserControllerImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import model.UserImpl;
import org.junit.Before;
import org.junit.Test;
import view.ViewImpl;

/**
 * This class consists of all the tests for the UserController class for flexible portfolios.
 */
public class UserControllerImplFlexiblePortfolioTest extends UserControllerImplTest {

  private PrintStream out;
  private ByteArrayOutputStream b;

  @Before
  public void setUp() {
    super.setUp();
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
    this.out.println("Enter 6 to create flexible portfolio "
        + "(You would be charged a commission fee of $3.33 per transaction)");
    this.out.println("Enter 7 to examine composition of flexible portfolio");
    this.out.println("Enter 8 to load a flexible portfolio");
    this.out.println("Enter 9 to buy stocks on a specific date "
        + "(You would be charged a commission fee of $3.33 per transaction)");
    this.out.println("Enter 10 to sell stocks on a specific date "
        + "(You would be charged a commission fee of $3.33 per transaction)");
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

  private void createFlexiblePortfolioHelper() {
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: ");
    this.out.println("Enter quantity of stocks: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");

  }

  private void enterStockHelper() {
    this.out.println("Enter ticker of stock you want to add to the portfolio: ");
    this.out.println("Enter quantity of stocks: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
  }

  @Test
  public void testCreateFlexiblePortfolio() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n100\n2022-10-21\n1\ntsla\n50\n2022-11-01\nq\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: \r\n" + "Enter the date (in format yyyy-MM-dd): \r\n"
        + "Enter 1 to add stocks to your portfolio\r\n" + "Enter q to exit");
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateFlexiblePortfolioWithAlreadyExistingName() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n6\np1\np2\n1\ntsla\n100\n2022-10-25\nq\nq\n"
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("The entered portfolio already exists. Try a different name!");
    this.out.println("Enter your portfolio name: ");
    this.addStockToPortfolio();
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateFlexiblePortfolioWithInvalidTicker() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\nhehe\naapl\n100\n2022-10-21\nq\nq\n".getBytes());
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
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateFlexiblePortfolioWithInvalidQuantity() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n1.2\n-3\n3\n2022-10-21\nq\nq\n".getBytes());
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
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testCreateFlexiblePortfolioWithInvalidDate() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n100\n2023-12-01\nq\nq\n".getBytes());
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
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("Date cannot be greater or equal to current date. Try a different date");
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());

  }

  @Test
  public void testLoadFlexiblePortfolio() {
    InputStream input = new ByteArrayInputStream(
        "samved\n8\nsamved_portfolios.json\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your file name (with extension): ");
    this.out.println("Flexible Portfolio loaded successfully!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testLoadFlexiblePortfolioWIthInvalidPath() {
    InputStream input = new ByteArrayInputStream("samved\n8\nhahaha.txt\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your file name (with extension): ");
    this.out.println("Error in loading flexible portfolio!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testLoadAndThenCheckCompositionOfFlexiblePortfolio() {
    InputStream input = new ByteArrayInputStream(
        "samved\n8\nsamved_portfolios.json\n7\np1\n2022-10-22\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    this.out.println("Enter your file name (with extension): ");
    this.out.println("Flexible Portfolio loaded successfully!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("The portfolio composition of portfolio p1 on 2022-10-22 is:-");
    this.out.println("{");
    this.out.println("  \"AAPL\" : 10");
    this.out.println("}");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }


  @Test
  public void testCompositionOfFlexiblePortfolio() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n100\n2022-10-21\nq\n7\np1\n2022-10-22\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("The portfolio composition of portfolio p1 on 2022-10-22 is:-");
    this.out.println("{");
    this.out.println("  \"AAPL\" : 100");
    this.out.println("}");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testBuyStocks() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n9\np1\naapl\n30\n2022-10-21\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: \r\n"
        + "Enter the date (in format yyyy-MM-dd): \r\n"
        + "Stocks bought successfully and added to your portfolio!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testBuyStocksOnWeekends() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n9\np1\naapl\n30\n2022-11-06\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter ticker of stock you want to add to the portfolio: \r\n"
        + "Enter quantity of stocks: \r\n"
        + "Enter the date (in format yyyy-MM-dd): \r\n"
        + "Stock market is closed at this date, so please enter a different date!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testBuyStocksWithInvalidPortfolioName() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n9\nhaha\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("The entered portfolio does not exist. Please enter a valid portfolio name");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testSellStocksWithInvalidPortfolioName() {

    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n11\n2022-10-21\nq\n9\nhaha\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("The entered portfolio does not exist. Please enter a valid portfolio name");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());


  }

  @Test
  public void testSellStocks() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n10\np1\naapl\n5\n2022-10-25\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Please enter the ticker of stock you want to sell\r\n"
        + "Enter quantity of stocks: \r\n"
        + "Enter the date (in format yyyy-MM-dd): \r\n"
        + "Stocks sold successfully!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testSellStocksOnWeekends() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n10\np1\naapl\n5\n2022-11-06\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Please enter the ticker of stock you want to sell\r\n"
        + "Enter quantity of stocks: \r\n"
        + "Enter the date (in format yyyy-MM-dd): \r\n"
        + "Stock market is closed at this date, so please enter a different date!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testSellStocksWithQtyMore() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n10\np1\naapl\n30\n2022-10-25\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Please enter the ticker of stock you want to sell\r\n"
        + "Enter quantity of stocks: \r\n"
        + "Enter the date (in format yyyy-MM-dd): \r\n"
        + "Quantity entered is more than what you have in your portfolio!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testSellStocksWithDateBeforeBought() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n10\np1\naapl\n5\n2022-10-20\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Please enter the ticker of stock you want to sell\r\n"
        + "Enter quantity of stocks: \r\n"
        + "Enter the date (in format yyyy-MM-dd): \r\n"
        + "Quantity entered is more than what you have in your portfolio!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

  @Test
  public void testTotalValueOfFlexiblePortfolio() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n11\np1\n2022-10-25\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("Portfolio_Name: p1\r\n"
        + "The stock valuation breakdown is: \r\n"
        + "AAPL : $ 1523.4\r\n"
        + "Portfolio_Valuation at 2022-10-25 is : $ 1523.4\r\n");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testTotalValueOfFlexiblePortfolioWhenMarketIsClosed() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n11\np1\n2022-11-13\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println(
        "Stock market is closed at the date: 2022-11-13. So please enter a different date\n");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testTotalValueOfFlexiblePortfolioBeforeAnyStockIsBought() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n11\np1\n2022-07-21\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("Portfolio_Valuation at 2022-07-21 is : $ 0.0\n");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testCostBasis() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n12\np1\n2022-10-30\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("Portfolio_Name: p1\n"
        + "Cost basis of your portfolio at 2022-10-30 is : $ 1476.03\n");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  @Test
  public void testCostBasisBeforeStockIsBought() {
    InputStream input = new ByteArrayInputStream(
        "samved\n6\np1\n1\naapl\n10\n2022-10-21\nq\n12\np1\n2022-07-21\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");

    this.outputMenu();
    this.out.println("Enter your portfolio name: ");
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
    this.out.println("Portfolio_Name: p1\n"
        + "Cost basis of your portfolio at 2022-07-21 is : $ 0.0\n");
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  private void chartHelperMonths() {
    this.out.println("Performance of portfolio p1 from 2022-01-01 to 2022-11-15\n"
        + "Jan 2022: *\n"
        + "Feb 2022: *\n"
        + "Mar 2022: *\n"
        + "Apr 2022: *********\n"
        + "May 2022: ********\n"
        + "Jun 2022: *******\n"
        + "Jul 2022: ***********\n"
        + "Aug 2022: ******\n"
        + "Sep 2022: *****\n"
        + "Oct 2022: *******\n"
        + "Nov 2022: ******\n"
        + "Scale: * = $1166");
  }

  @Test
  public void testViewPerformanceOver11Months() {
    InputStream input = new ByteArrayInputStream(
        ("samved\n6\np1\n1\naapl\n10\n2022-01-12\n1\ntsla\n10\n2022-04-13\n1\nmsft\n10\n"
            + "2022-07-21\n1\naapl\n10\n2022-10-21\nq\n13\np1\n2022-01-01\n2022-11-15\nq\n")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();

    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: \n"
        + "Enter the start date (in format yyyy-MM-dd): \n"
        + "Enter the end date (in format yyyy-MM-dd): ");
    chartHelperMonths();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  private void chartHelperDays() {
    this.out.println("Performance of portfolio p1 from 2022-10-16 to 2022-11-15\n"
        + "2022-10-16: ********************************\n"
        + "2022-10-17: ********************************\n"
        + "2022-10-18: ********************************\n"
        + "2022-10-19: ********************************\n"
        + "2022-10-20: *******************************\n"
        + "2022-10-21: ****************************************\n"
        + "2022-10-22: ****************************************\n"
        + "2022-10-23: ****************************************\n"
        + "2022-10-24: ****************************************\n"
        + "2022-10-25: *****************************************\n"
        + "2022-10-26: ****************************************\n"
        + "2022-10-27: ***************************************\n"
        + "2022-10-28: *****************************************\n"
        + "2022-10-29: ****************************************\n"
        + "2022-10-30: ****************************************\n"
        + "2022-10-31: ****************************************\n"
        + "2022-11-01: ****************************************\n"
        + "2022-11-02: **************************************\n"
        + "2022-11-03: *************************************\n"
        + "2022-11-04: *************************************\n"
        + "2022-11-05: *************************************\n"
        + "2022-11-06: *************************************\n"
        + "2022-11-07: *************************************\n"
        + "2022-11-08: *************************************\n"
        + "2022-11-09: ***********************************\n"
        + "2022-11-10: **************************************\n"
        + "2022-11-11: ***************************************\n"
        + "2022-11-12: **************************************\n"
        + "2022-11-13: **************************************\n"
        + "2022-11-14: **************************************\n"
        + "2022-11-15: ***************************************\n"
        + "Scale: * = $190");
  }

  @Test
  public void testViewPerformanceOver30days() {
    InputStream input = new ByteArrayInputStream(
        ("samved\n6\np1\n1\naapl\n10\n2022-01-12\n1\ntsla\n10\n2022-04-13\n1\nmsft\n10\n"
            + "2022-07-21\n1\naapl\n10\n2022-10-21\nq\n13\np1\n2022-10-16\n2022-11-15\nq\n")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();

    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: \n"
        + "Enter the start date (in format yyyy-MM-dd): \n"
        + "Enter the end date (in format yyyy-MM-dd): ");
    chartHelperDays();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  private void chartHelperWeeks() {
    this.out.println("Performance of portfolio p1 from 2022-07-16 to 2022-09-05\n"
        + "2022-07-16: *************\n"
        + "2022-07-23: ******************\n"
        + "2022-07-30: *******************\n"
        + "2022-08-06: *******************\n"
        + "2022-08-13: ********************\n"
        + "2022-08-20: *******************\n"
        + "2022-08-27: **********\n"
        + "Scale: * = $683");
  }

  @Test
  public void testViewPerformanceOver50days() {
    InputStream input = new ByteArrayInputStream(
        ("samved\n6\np1\n1\naapl\n10\n2022-01-12\n1\ntsla\n10\n2022-04-13\n1\nmsft\n10\n"
            + "2022-07-21\n1\naapl\n10\n2022-10-21\nq\n13\np1\n2022-07-16\n2022-09-05\nq\n")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();

    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: \n"
        + "Enter the start date (in format yyyy-MM-dd): \n"
        + "Enter the end date (in format yyyy-MM-dd): ");
    chartHelperWeeks();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  private void chartHelperYears() {
    this.out.println("Performance of portfolio p1 from 2017-01-17 to 2022-11-10\n"
        + "2017: ****\n"
        + "2018: ****\n"
        + "2019: *******\n"
        + "2020: ********\n"
        + "2021: **************\n"
        + "2022: ******\n"
        + "Scale: * = $1257");
  }

  @Test
  public void testViewPerformanceOver5Years() {
    InputStream input = new ByteArrayInputStream(
        ("samved\n6\np1\n1\naapl\n10\n2012-01-12\n1\ntsla\n10\n2017-04-13\n1\nmsft\n10\n"
            + "2019-07-25\n1\naapl\n10\n2021-10-25\nq\n13\np1\n2017-01-17\n2022-11-10\nq\n")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();

    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: \n"
        + "Enter the start date (in format yyyy-MM-dd): \n"
        + "Enter the end date (in format yyyy-MM-dd): ");
    chartHelperYears();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

  private void chartHelperManyMonths() {
    this.out.println("Performance of portfolio p1 from 2017-01-17 to 2019-04-17\n"
        + "Jan 2017: ***\n"
        + "Feb 2017: ***\n"
        + "Mar 2017: ***\n"
        + "Apr 2017: ***********\n"
        + "May 2017: ***********\n"
        + "Jun 2017: ************\n"
        + "Jul 2017: ***********\n"
        + "Aug 2017: ************\n"
        + "Sep 2017: ***********\n"
        + "Oct 2017: ************\n"
        + "Nov 2017: ***********\n"
        + "Dec 2017: ***********\n"
        + "Jan 2018: ************\n"
        + "Feb 2018: ************\n"
        + "Mar 2018: **********\n"
        + "Apr 2018: ***********\n"
        + "May 2018: ***********\n"
        + "Jun 2018: ************\n"
        + "Jul 2018: ***********\n"
        + "Aug 2018: ************\n"
        + "Sep 2018: ************\n"
        + "Oct 2018: *************\n"
        + "Nov 2018: ************\n"
        + "Dec 2018: ***********\n"
        + "Jan 2019: ***********\n"
        + "Feb 2019: ***********\n"
        + "Mar 2019: ***********\n"
        + "Apr 2019: ***********\n"
        + "Scale: * = $434");
  }

  @Test
  public void testViewPerformanceOverManyMonths() {
    InputStream input = new ByteArrayInputStream(
        ("samved\n6\np1\n1\naapl\n10\n2012-01-12\n1\ntsla\n10\n2017-04-13\n1\nmsft\n10\n"
            + "2019-07-25\n1\naapl\n10\n2021-10-25\nq\n13\np1\n2017-01-17\n2019-04-17\nq\n")
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(),
        new ViewImpl());
    controller.runGo();
    this.createFlexiblePortfolioHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();
    this.addStockToPortfolio();
    enterStockHelper();

    this.addStockToPortfolio();
    this.out.println("Portfolio created successfully!!");
    this.outputMenu();
    this.out.println("Enter your portfolio name: \n"
        + "Enter the start date (in format yyyy-MM-dd): \n"
        + "Enter the end date (in format yyyy-MM-dd): ");
    chartHelperManyMonths();
    this.outputMenu();
    assertEquals(b.toString().replace("\r", ""), bytes.toString()
        .replace("\r", ""));
  }

}
