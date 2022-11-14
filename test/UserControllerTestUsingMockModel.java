import static org.junit.Assert.assertEquals;

import controller.UserController;
import controller.UserControllerImpl;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import model.Stocks;
import model.User;
import org.junit.Before;
import org.junit.Test;
import view.ViewImpl;

/**
 * This is a UserController Test class which uses mock model. The purpose of this class testing is
 * to validate the proper flow with a dummy model. This class maintains and compares a log of all
 * methods that are called in a model with their arguments and then validates it against that.
 */
public class UserControllerTestUsingMockModel {

  private PrintStream out;
  private ByteArrayOutputStream b;

  @Before
  public void setUp() {
    b = new ByteArrayOutputStream();
    out = new PrintStream(b);
  }

  @Test
  public void testCreatePortfolio() {
    InputStream input = new ByteArrayInputStream("sanket\n1\nport1\n1\naapl\n100\nq\nq\n"
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);

    ByteArrayOutputStream mock = new ByteArrayOutputStream();
    PrintStream mock_output = new PrintStream(mock);
    User mockUser = new MockUser(mock_output);
    UserController controller = new UserControllerImpl(input, output, mockUser, new ViewImpl());
    controller.runGo();
    out.println("setName called with name as: sanket");
    out.println("getName called");
    out.println("checkPortfolioExists called with pName as: port1");
    out.println("ifStocksExist called with ticker as: aapl");
    out.println("Create Portfolio called with portfolioName: port1 and stocks map as: {AAPL=100}");
    assertEquals(b.toString(), mock.toString());
  }

  @Test
  public void testGetPortfolioComposition() {
    InputStream input = new ByteArrayInputStream("sanket\n1\nport1\n1\naapl\n100\nq\n2\nport1\nq\n"
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    ByteArrayOutputStream mock = new ByteArrayOutputStream();
    PrintStream mock_output = new PrintStream(mock);
    User mockUser = new MockUser(mock_output);
    UserController controller = new UserControllerImpl(input, output, mockUser, new ViewImpl());
    controller.runGo();
    out.println("setName called with name as: sanket");
    out.println("getName called");
    out.println("checkPortfolioExists called with pName as: port1");
    out.println("ifStocksExist called with ticker as: aapl");
    out.println("Create Portfolio called with portfolioName: port1 and stocks map as: {AAPL=100}");
    out.println("getPortfolioComposition called with pName as: port1");
    assertEquals(b.toString(), mock.toString());
  }

  @Test
  public void testLoadPortfolio() {
    InputStream input = new ByteArrayInputStream("sanket\n5\nportfolio.xml\nq\n"
            .getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    ByteArrayOutputStream mock = new ByteArrayOutputStream();
    PrintStream mock_output = new PrintStream(mock);
    User mockUser = new MockUser(mock_output);
    UserController controller = new UserControllerImpl(input, output, mockUser, new ViewImpl());
    controller.runGo();
    out.println("setName called with name as: sanket");
    out.println("getName called");
    out.println("loadPortfolio called with pfName as: portfolio.xml");
    assertEquals(b.toString(), mock.toString());
  }

  @Test
  public void testGetPortfoliosName() {
    InputStream input = new ByteArrayInputStream("sanket\n4\nq\n".getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    ByteArrayOutputStream mock = new ByteArrayOutputStream();
    PrintStream mock_output = new PrintStream(mock);
    User mockUser = new MockUser(mock_output);
    UserController controller = new UserControllerImpl(input, output, mockUser, new ViewImpl());
    controller.runGo();
    out.println("setName called with name as: sanket");
    out.println("getName called");
    out.println("getPortfoliosName called");
    assertEquals(b.toString(), mock.toString());
  }

  @Test
  public void testGetTotalValuation() {
    InputStream input = new ByteArrayInputStream(("sanket\n3\ntest_portfolio\n2022-10-28"
            + "\nq\n").getBytes());
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream output = new PrintStream(bytes);
    ByteArrayOutputStream mock = new ByteArrayOutputStream();
    PrintStream mock_output = new PrintStream(mock);
    User mockUser = new MockUser(mock_output);
    UserController controller = new UserControllerImpl(input, output, mockUser, new ViewImpl());
    controller.runGo();
    out.println("setName called with name as: sanket");
    out.println("getName called");
    out.println("isValidFormat called with value as: 2022-10-28");
    out.println("getTotalValuation called with date as: 2022-10-28 and pName as: test_portfolio");
    assertEquals(b.toString(), mock.toString());
  }

  /**
   * This is mock user class which implements the User interface. This class maintains a log, in
   * form of a printStream object and ensures that the actual input that the user enters is the
   * input that is passed to the model.
   */
  public class MockUser implements User {

    private PrintStream out;

    public MockUser(PrintStream out) {
      this.out = out;
    }

    @Override
    public void createPortfolio(String portfolioName, Map<String, Integer> stocks)
            throws IllegalArgumentException {
      out.println("Create Portfolio called with portfolioName: " + portfolioName
              + " and stocks map " + "as: " + stocks);
    }

    @Override
    public StringBuilder getPortfolioComposition(String pName) {
      out.println("getPortfolioComposition called with pName as: " + pName);
      StringBuilder temp = new StringBuilder("Testing");
      return temp;
    }

    @Override
    public String loadPortfolio(String pfName) throws IllegalArgumentException {
      out.println("loadPortfolio called with pfName as: " + pfName);
      return "Testing";
    }

    @Override
    public boolean isValidFormat(String value) {
      out.println("isValidFormat called with value as: " + value);
      return true;
    }

    @Override
    public StringBuilder getTotalValuation(String date, String pName) {
      out.println("getTotalValuation called with date as: " + date + " and pName as: " + pName);
      StringBuilder temp = new StringBuilder("Testing");
      return temp;
    }

    @Override
    public boolean ifStocksExist(String ticker) {
      out.println("ifStocksExist called with ticker as: " + ticker);
      return true;
    }

    @Override
    public String getName() {
      out.println("getName called");
      return "Testing";
    }

    @Override
    public void setName(String name) {
      out.println("setName called with name as: " + name);
    }

    @Override
    public StringBuilder getPortfoliosName() {
      out.println("getPortfoliosName called");
      StringBuilder temp = new StringBuilder("Testing");
      return temp;
    }

    @Override
    public int checkPortfolioExists(String pName) {
      out.println("checkPortfolioExists called with pName as: " + pName);
      return 0;
    }

    @Override
    public void createFlexiblePortfolio(String portfolioName, List<Stocks> stocks) {

    }

    @Override
    public StringBuilder getFlexiblePortfolioComposition(String pName,String date) {
      return null;
    }

    @Override
    public String loadFlexiblePortfolio(String fileName) throws IllegalArgumentException {
      return null;
    }

    @Override
    public String buyStocks(String ticker, int qty, String pName, String date) {
      return null;
    }

    @Override
    public String sellStocks(String ticker, int qty, String pName, String date) {
      return null;
    }

    @Override
    public void display() {

    }

    @Override
    public StringBuilder getFlexiblePortfolioTotalValuation(String date, String pName) {
      return null;
    }

    @Override
    public boolean validateDateAccToApi(String ticker, String date) {
      return false;
    }

    @Override
    public StringBuilder getCostBasis(String date, String pName) {
      return null;
    }
  }
}