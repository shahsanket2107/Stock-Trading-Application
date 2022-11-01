import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import model.Portfolio;
import model.PortfolioImpl;
import model.User;
import model.UserImpl;

import static org.junit.Assert.assertEquals;

public class UserImplTest {
  private String name;
  private User u;

  @Before
  public void setup() {
    Portfolio p;
    ArrayList<Portfolio> list = new ArrayList<>();
    name = "John Doe";
    Map<String, Integer> m = new HashMap<>();
    Map<String, Integer> m1 = new HashMap<>();
    Map<String, Integer> m2 = new HashMap<>();
    m.put("aapl", 20);
    p = new PortfolioImpl("test_portfolio_single_stock", m);
    list.add(p);
    m1.put("aapl", 20);
    m1.put("googl", 5);
    p = new PortfolioImpl("test_portfolio_two_stock", m1);
    list.add(p);
    m2.put("aapl", 20);
    m2.put("googl", 5);
    m2.put("tsla", 22);
    p = new PortfolioImpl("test_portfolio_three_stock", m2);
    list.add(p);
    u = new UserImpl(name, list);
  }

  @Test
  public void testGetName() {
    assertEquals("John Doe", u.getName());
  }


  @Test
  public void testGetPortfoliosName() {
    assertEquals("The list of portfolios is:\n" +
            "test_portfolio_single_stock\n" +
            "test_portfolio_two_stock\n" +
            "test_portfolio_three_stock\n", u.getPortfoliosName().toString());
  }

  @Test
  public void testValidCreatePortfolioForUserWithNoPortfolios() {
    Map<String, Integer> m = new HashMap<>();
    User u1 = new UserImpl();
    m.put("tsla", 10);
    m.put("zs", 13);
    m.put("amzn", 3);
    u1.createPortfolio("testNewPortfolio", m);
    assertEquals("The list of portfolios is:\n" +
            "testNewPortfolio\n", u1.getPortfoliosName().toString());
    assertEquals("Portfolio_Name: testNewPortfolio\n" +
            "{\n" +
            "\tStock_Ticker: tsla\n" +
            "\tQuantity: 10\n" +
            "}\n" +
            "{\n" +
            "\tStock_Ticker: zs\n" +
            "\tQuantity: 13\n" +
            "}\n" +
            "{\n" +
            "\tStock_Ticker: amzn\n" +
            "\tQuantity: 3\n" +
            "}\n" +
            "\n\n", u1.getPortfolioComposition("testNewPortfolio").toString());
  }

  @Test
  public void testValidCreatePortfolioForUserWithExistingPortfolios() {
    Map<String, Integer> m = new HashMap<>();
    User u1 = new UserImpl();
    m.put("tsla", 10);
    m.put("zs", 13);
    m.put("amzn", 3);
    u1.createPortfolio("testNewPortfolio", m);
    assertEquals("The list of portfolios is:\n" +
            "testNewPortfolio\n", u1.getPortfoliosName().toString());
    assertEquals("Portfolio_Name: testNewPortfolio\n" +
            "{\n" +
            "\tStock_Ticker: tsla\n" +
            "\tQuantity: 10\n" +
            "}\n" +
            "{\n" +
            "\tStock_Ticker: zs\n" +
            "\tQuantity: 13\n" +
            "}\n" +
            "{\n" +
            "\tStock_Ticker: amzn\n" +
            "\tQuantity: 3\n" +
            "}\n" +
            "\n\n", u1.getPortfolioComposition("testNewPortfolio").toString());
  }

  @Test
  public void testLoadPortfolio() {
  }

  @Test
  public void testIfStocksExist() {
  }

  @Test
  public void testIsValidFormat() {
  }

  @Test
  public void testGetTotalValuation() {
  }

  @Test
  public void testGetPortfolioCompositionForPortfolio3() {
    assertEquals("Portfolio_Name: test_portfolio_three_stock\n" +
            "{\n" +
            "\tStock_Ticker: aapl\n" +
            "\tQuantity: 20\n" +
            "}\n" +
            "{\n" +
            "\tStock_Ticker: tsla\n" +
            "\tQuantity: 22\n" +
            "}\n" +
            "{\n" +
            "\tStock_Ticker: googl\n" +
            "\tQuantity: 5\n" +
            "}\n\n\n", u.getPortfolioComposition("test_portfolio_three_stock").toString());
  }

  @Test
  public void testGetPortfolioCompositionForPortfolio2() {
    assertEquals("Portfolio_Name: test_portfolio_two_stock\n" +
            "{\n" +
            "\tStock_Ticker: aapl\n" +
            "\tQuantity: 20\n" +
            "}\n" +
            "{\n" +
            "\tStock_Ticker: googl\n" +
            "\tQuantity: 5\n" +
            "}\n\n\n", u.getPortfolioComposition("test_portfolio_two_stock").toString());
  }

  @Test
  public void testGetPortfolioCompositionForPortfolio1() {
    assertEquals("Portfolio_Name: test_portfolio_single_stock\n" +
            "{\n" +
            "\tStock_Ticker: aapl\n" +
            "\tQuantity: 20\n" +
            "}\n\n\n", u.getPortfolioComposition("test_portfolio_single_stock").toString());
  }

  @Test
  public void testGetPortfolioCompositionForPortfolioThatDoesNotExist() {
    assertEquals("The given portfolio name does not exist!!\n" +
            "Please enter a valid portfolio name!!", u.getPortfolioComposition
            ("Not_existing_portfolio").toString());
  }

  @Test
  public void testCheckPortfolioExists() {
    assertEquals(true, u.checkPortfolioExists("test_portfolio_single_stock"));
    assertEquals(true, u.checkPortfolioExists("test_portfolio_two_stock"));
    assertEquals(true, u.checkPortfolioExists("test_portfolio_three_stock"));
    assertEquals(false, u.checkPortfolioExists("Not_existing_portfolio"));

  }
}