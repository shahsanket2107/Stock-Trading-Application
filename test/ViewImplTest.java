import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import view.View;
import view.ViewImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class tests the ViewImpl class. So this basically has an OutputStream where we write data
 * and assert it with actual view data.
 */
public class ViewImplTest {

  private PrintStream out;

  private PrintStream out1;
  private ByteArrayOutputStream b;

  private ByteArrayOutputStream b1;
  private View v;

  @Before
  public void Setup() {
    b = new ByteArrayOutputStream();
    out = new PrintStream(b);
    v = new ViewImpl();
    v.setStream(out);
    b1 = new ByteArrayOutputStream();
    out1 = new PrintStream(b1);
  }

  @Test
  public void testGetMenu() {
    v.getMenu();
    out1.println();
    out1.println("Enter 1 for making portfolio");
    out1.println("Enter 2 to examine the composition of a particular portfolio");
    out1.println("Enter 3 to determine the total value of portfolio on a certain date");
    out1.println("Enter 4 to view all portfolio names");
    out1.println("Enter 5 to load your portfolio");
    out1.println("Enter q to exit");
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
  public void getDate() {
    v.getDate();
    out1.println("Enter the date (in format yyyy-MM-dd): ");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void displayMessage() {
    String message="This is a test message!";
    v.displayMessage(message);
    out1.println(message);
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void invalidDate() {
    v.invalidDate();
    out1.println("Date is not in proper format!!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void seeDefault() {
    v.seeDefault();
    out1.println("Invalid input. Please try again!");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void getName() {
    v.getName();
    out1.println("Please Enter your name:");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void displayName() {
    String name="Sanket";
    v.displayName(name);
    out1.println("Welcome " + name + " !!\nPlease select an option from the menu!!\n");
    assertEquals(b1.toString(), b.toString());
  }

  @Test
  public void alreadyExists() {
    v.alreadyExists();
    out1.println("The entered portfolio already exists. Try a different name!");
    assertEquals(b1.toString(), b.toString());
  }
}