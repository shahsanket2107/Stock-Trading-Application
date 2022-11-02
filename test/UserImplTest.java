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

/**
 * In this test class we test the UserImpl model. We test various methods that are used by the user
 * like createPortfolio,loadPortfolio, getPortfolioNames, getPortfolioComposition etc. For invalid
 * this class only tests those invalid inputs that are handled in this UserImpl class. Some
 * invalid inputs are invalidated at view so those are tested there.
 */
public class UserImplTest {
  private User u;

  @Before
  public void setup() {
    Portfolio p;
    ArrayList<Portfolio> list = new ArrayList<>();
    String name = "John Doe";
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
    assertEquals(true, u1.checkPortfolioExists("testNewPortfolio"));
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
    m.put("tsla", 10);
    m.put("zs", 13);
    m.put("amzn", 3);
    u.createPortfolio("testNewPortfolio", m);
    assertEquals(true, u.checkPortfolioExists("testNewPortfolio"));
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
            "\n\n", u.getPortfolioComposition("testNewPortfolio").toString());
  }

  @Test
  public void testWritingToFileOnSuccessfulCreationOfPortfolio() {
    Map<String, Integer> m = new HashMap<>();
    User u1 = new UserImpl("Sanket", new ArrayList<>());
    m.put("tsla", 10);
    m.put("zs", 13);
    m.put("amzn", 3);
    u1.createPortfolio("testNewPortfolio", m);
    assertEquals("Portfolio loaded successfully!",
            u1.loadPortfolio("Sanket_portfolios.xml"));
    assertEquals(true, u1.checkPortfolioExists("testNewPortfolio"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAlreadyExistingPortfolio() {
    Map<String, Integer> m = new HashMap<>();
    m.put("tsla", 10);
    m.put("zs", 13);
    m.put("amzn", 3);
    u.createPortfolio("testNewPortfolio", m);
    Map<String, Integer> m2 = new HashMap<>();
    m2.put("tsla", 10);
    m2.put("zs", 13);
    u.createPortfolio("testNewPortfolio", m2);
  }

  @Test
  public void testLoadPortfolio() {
    assertEquals("Portfolio loaded successfully!",
            u.loadPortfolio("portfolio.xml"));
  }

  @Test
  public void testLoadPortfolioFailDueTOInvalidName() {
    assertEquals("Invalid file name. Please try again!",
            u.loadPortfolio("NotExistingPortfolio.xml"));
  }

  @Test
  public void testLoadPortfolioFailDueTOInvalidType() {
    assertEquals("Invalid file format. Only xml files can be loaded!",
            u.loadPortfolio("pf.txt"));
  }

  @Test
  public void testIfStocksExist() {
    assertEquals(true, u.ifStocksExist("aapl"));
    assertEquals(true, u.ifStocksExist("zs"));
    assertEquals(true, u.ifStocksExist("amzn"));
    assertEquals(true, u.ifStocksExist("googl"));
    assertEquals(false, u.ifStocksExist("test"));
    assertEquals(false, u.ifStocksExist("invalid"));
    assertEquals(false, u.ifStocksExist("dummy"));
  }

  @Test
  public void testIsValidFormat() {
    assertEquals(true, u.isValidFormat("2022-11-01"));
    assertEquals(false, u.isValidFormat("2022-11-1"));
    assertEquals(false, u.isValidFormat("2022/11/01"));
    assertEquals(false, u.isValidFormat("1-11-2022"));
    assertEquals(false, u.isValidFormat("1-11-22"));
    assertEquals(false, u.isValidFormat("2022-18-2"));
    assertEquals(false, u.isValidFormat("2022-8-40"));
    assertEquals(false, u.isValidFormat("2022-18-60"));
  }

  @Test
  public void testGetTotalValuationForPortFolio1() {
    assertEquals("Portfolio_Name: test_portfolio_single_stock\n" +
            "Portfolio_Valuation at 2022-10-28 is : $ 3114.8\n" +
            "The stock valuation breakdown is: \n" +
            "aapl : $3114.8\n", u.getTotalValuation("2022-10-28",
            "test_portfolio_single_stock").toString());
  }

  @Test
  public void testGetTotalValuationForPortFolio2() {
    assertEquals("Portfolio_Name: test_portfolio_two_stock\n" +
            "Portfolio_Valuation at 2022-10-28 is : $ 3596.25\n" +
            "The stock valuation breakdown is: \n" +
            "aapl : $3114.8\n" +
            "googl : $481.45000000000005\n", u.getTotalValuation("2022-10-28",
            "test_portfolio_two_stock").toString());
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
            "Please enter a valid portfolio name!!",
            u.getPortfolioComposition("Not_existing_portfolio").toString());
  }

  @Test
  public void testCheckPortfolioExists() {
    assertEquals(true, u.checkPortfolioExists("test_portfolio_single_stock"));
    assertEquals(true, u.checkPortfolioExists("test_portfolio_two_stock"));
    assertEquals(true, u.checkPortfolioExists("test_portfolio_three_stock"));
    assertEquals(false, u.checkPortfolioExists("Not_existing_portfolio"));
  }
}