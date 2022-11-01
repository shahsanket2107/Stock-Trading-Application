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
  private ArrayList<Portfolio> list;
  private Map<String, Integer> m;
  private Map<String, Integer> m1;
  private Map<String, Integer> m2;
  private Portfolio p;
  private Portfolio p1;
  private Portfolio p2;
  private User u;

  @Before
  public void setup() {
    name = "John Doe";
    m = new HashMap<>();
    m1 = new HashMap<>();
    m2 = new HashMap<>();
    list = new ArrayList<>();
    m.put("aapl", 20);
    p = new PortfolioImpl("test_portfolio_single_stock", m);
    list.add(p);
    m1.put("aapl", 20);
    m1.put("googl", 5);
    p1 = new PortfolioImpl("test_portfolio_two_stock", m1);
    list.add(p1);
    m2.put("aapl", 20);
    m2.put("googl", 5);
    m2.put("tsla", 22);
    p2 = new PortfolioImpl("test_portfolio_three_stock", m2);
    list.add(p2);
    u = new UserImpl(name, list);
  }

  @Test
  public void getName() {
    assertEquals("John Doe", u.getName());
  }


  @Test
  public void getPortfoliosName() {
    assertEquals("The list of portfolios is:\n" +
            "test_portfolio_single_stock\n" +
            "test_portfolio_two_stock\n" +
            "test_portfolio_three_stock\n", u.getPortfoliosName().toString());
  }

  @Test
  public void createPortfolio() {
  }

  @Test
  public void loadPortfolio() {
  }

  @Test
  public void ifStocksExist() {
  }

  @Test
  public void isValidFormat() {
  }

  @Test
  public void getTotalValuation() {
  }

  @Test
  public void getPortfolioCompositionForPortfolio3() {
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
  public void getPortfolioCompositionForPortfolio2() {
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
  public void getPortfolioCompositionForPortfolio1() {
    assertEquals("Portfolio_Name: test_portfolio_single_stock\n" +
            "{\n" +
            "\tStock_Ticker: aapl\n" +
            "\tQuantity: 20\n" +
            "}\n\n\n", u.getPortfolioComposition("test_portfolio_single_stock").toString());
  }

  @Test
  public void getPortfolioCompositionForPortfolioThatDoesNotExist() {
    assertEquals("The given portfolio name does not exist!!\n" +
            "Please enter a valid portfolio name!!", u.getPortfolioComposition
            ("Not_existing_portfolio").toString());
  }

  @Test
  public void checkPortfolioExists() {
    assertEquals(true,u.checkPortfolioExists("test_portfolio_single_stock"));
    assertEquals(true,u.checkPortfolioExists("test_portfolio_two_stock"));
    assertEquals(true,u.checkPortfolioExists("test_portfolio_three_stock"));
    assertEquals(false,u.checkPortfolioExists("Not_existing_portfolio"));

  }
}