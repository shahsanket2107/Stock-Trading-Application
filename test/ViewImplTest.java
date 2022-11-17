import static org.junit.Assert.assertEquals;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import org.junit.Before;
import org.junit.Test;
import view.View;
import view.ViewImpl;

/**
 * This class tests the ViewImpl class. So this basically has an OutputStream where we write data
 * and assert it with actual view data.
 */
public class ViewImplTest {

  private PrintStream out1;
  private ByteArrayOutputStream b;
  private ByteArrayOutputStream b1;
  private View v;

  @Before
  public void setUp() {
    b = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(b);
    v = new ViewImpl();
    v.setStream(out);
    b1 = new ByteArrayOutputStream();
    out1 = new PrintStream(b1);
  }

  @Test
  public void testGetMenu() {
    v.getMenu();
    this.out1.println();
    this.out1.println("Enter 1 for making portfolio");
    this.out1.println("Enter 2 to examine the composition of a particular portfolio");
    this.out1.println("Enter 3 to determine the total value of portfolio on a certain date");
    this.out1.println("Enter 4 to view all portfolio names");
    this.out1.println("Enter 5 to load your portfolio");
    this.out1.println("Enter 6 to create flexible portfolio");
    this.out1.println("Enter 7 to examine composition of flexible portfolio");
    this.out1.println("Enter 8 to load a flexible portfolio");
    this.out1.println("Enter 9 to buy stocks on a specific date");
    this.out1.println("Enter 10 to sell stocks on a specific date");
    this.out1.println(
        "Enter 11 to determine the total value of flexible portfolio on a certain " + "date");
    this.out1.println("Enter 12 to find cost basis of a flexible portfolio on a certain date");
    this.out1.println(
        "Enter 13 to view how the portfolio has performed over a period of time using chart");
    this.out1.println("Enter q to exit");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetAddStockMenu() {
    v.getAddStockMenu();
    out1.println("Enter 1 to add stocks to your portfolio");
    out1.println("Enter q to exit");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetTicker() {
    v.getTicker();
    out1.println("Enter ticker of stock you want to add to the portfolio: ");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetQty() {
    v.getQty();
    out1.println("Enter quantity of stocks: ");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testQtyPositive() {
    v.qtyPositive();
    out1.println("Quantity should be a positive value!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testQtyInteger() {
    v.qtyInteger();
    out1.println("Quantity should be an integer value!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testInvalidTicker() {
    v.invalidTicker();
    out1.println("This ticker does not exist!! Please enter a valid ticker");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetPortfolioName() {
    v.getPortfolioName();
    out1.println("Enter your portfolio name: ");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testEmptyPortfolioMessage() {
    v.emptyPortfolioMessage();
    out1.println("Portfolio name cannot be empty!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetFileName() {
    v.getFileName();
    out1.println("Enter your file name (with extension): ");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetDate() {
    v.getDate();
    out1.println("Enter the date (in format yyyy-MM-dd): ");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testDisplayMessage() {
    String message = "This is a test message!";
    v.displayMessage(message);
    out1.println(message);
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testInvalidDate() {
    v.invalidDate();
    out1.println("Date is not in proper format!!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testSeeDefault() {
    v.seeDefault();
    out1.println("Invalid input. Please try again!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetName() {
    v.getName();
    out1.println("Please Enter your name:");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testDisplayName() {
    String name = "Sanket";
    v.displayName(name);
    out1.println("Welcome " + name + " !! Please select an option from the menu!!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testAlreadyExists() {
    v.alreadyExists();
    out1.println("The entered portfolio already exists. Try a different name!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testGetPortfolioMessage() {
    v.getPortfolioMessage();
    out1.println("Portfolio created successfully!!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void testPortfolioNotExist() {
    v.portfolioNotExist();
    out1.println("The entered portfolio does not exist. Please enter a valid portfolio name");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void sellStock() {
    v.sellStock();
    out1.println("Please enter the ticker of stock you want to sell");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void dataNotFound() {
    v.dataNotFound();
    out1.println("Stock market is closed at this date, so please enter a different date!!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void getStartDate() {
    v.getStartDate();
    out1.println("Enter the start date (in format yyyy-MM-dd): ");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void getEndDate() {
    v.getEndDate();
    out1.println("Enter the end date (in format yyyy-MM-dd): ");
    assertEquals(b1.toString(), b.toString());
  }
}