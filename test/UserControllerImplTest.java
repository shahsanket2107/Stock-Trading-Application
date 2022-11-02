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

public class UserControllerImplTest {

  private PrintStream out;
  private ByteArrayOutputStream b;

  @Before
  public void Setup() {
    b = new ByteArrayOutputStream();
    out = new PrintStream(b);
  }
  private void outputMenu(){
    this.out.println("");
    this.out.println("Enter 1 for making portfolio");
    this.out.println("Enter 2 to examine the composition of a particular portfolio");
    this.out.println("Enter 3 to determine the total value of portfolio on a certain date");
    this.out.println("Enter 4 to view all portfolio names");
    this.out.println("Enter 5 to load your portfolio");
    this.out.println("Enter q to exit");
  }
  @Test
  public void testMenu() {
    InputStream input = new ByteArrayInputStream("samved\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(), new ViewImpl());
    controller.go();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !! Please select an option from the menu!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }
  @Test
  public void testCreatePortfolio(){
    InputStream input = new ByteArrayInputStream("samved\n1\np1\n1\naapl\n100\nq\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(), new ViewImpl());
    controller.go();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " +"samved"+ " !! Please select an option from the menu!!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }
  @Test
  public void testViewAllPortfolios(){
    InputStream input = new ByteArrayInputStream("samved\n4\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(), new ViewImpl());
    controller.go();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !!\nPlease select an option from the menu!!\n");
    this.outputMenu();
    this.out.println("No portfolios exist at this time!! ");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }
  @Test
  public void testLoadPortfolio(){
    InputStream input = new ByteArrayInputStream("samved\n5\nsamved_portfolios.xml\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    UserController controller = new UserControllerImpl(input, output, new UserImpl(), new ViewImpl());
    controller.go();
    this.out.println("Please Enter your name:");
    this.out.println("Welcome " + "samved" + " !!\nPlease select an option from the menu!!\n");
    this.outputMenu();
    this.out.println("Portfolio loaded successfully!");
    this.outputMenu();
    assertEquals(b.toString(), bytes.toString());
  }

}