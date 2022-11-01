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
  private Portfolio p;
  private User u;

  @Before
  public void setup() {
    name = "John Doe";
    m = new HashMap<>();
    list = new ArrayList<>();
    m.put("aapl", 20);
    p = new PortfolioImpl("test_portfolio_single_stock", m);
    list.add(p);
    m.put("googl", 5);
    p = new PortfolioImpl("test_portfolio_two_stock", m);
    list.add(p);
//    m.put("tsla", 22);
//    p = new PortfolioImpl("test_portfolio_three_stock", m);
//    list.add(p);
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
  public void getPortfolioCompositionForPortfolio1() {
    assertEquals("Portfolio_Name: test_portfolio_two_stock\n" +
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
            "}\n\n\n", u.getPortfolioComposition("test_portfolio_two_stock").toString());
  }

  @Test
  public void checkPortfolioExists() {
    System.out.println(u.getPortfolioComposition("test_portfolio_single_stock"));
    System.out.println(u.getPortfolioComposition("test_portfolio_two_stock"));
  }
}