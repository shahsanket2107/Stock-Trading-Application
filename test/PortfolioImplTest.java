import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.Portfolio;
import model.PortfolioImpl;

import static org.junit.Assert.assertEquals;

/**
 * In this test class we test the PortfolioImpl model. getValuationAtDate method is tested by
 * passing in static values of quantity, ticker and date for valid as well as invalid inputs.
 */

public class PortfolioImplTest {

  private Map<String, Integer> stocks;
  private String name;
  private Portfolio p;

  @Before
  public void setup() {
    name = "test_portfolio";
    stocks = new HashMap<>();
    stocks.put("aapl", 25);
    p = new PortfolioImpl(name, stocks);
  }

  @Test
  public void testGetValuationAtDateForTwoStockPortfolio() {
    int qty1 = 25;
    double price1 = 155.74;
    double temp1 = price1 * qty1;
    stocks.put("tsla", 25);

    Portfolio p2 = new PortfolioImpl(name, stocks);
    int qty2 = 25;
    double price2 = 228.52;
    double temp2 = price2 * qty2;

    Map<String, Double> check = new HashMap<>();
    check.put("aapl", temp1);
    check.put("tsla", temp2);
    Map<String, Double> m = p2.getValuationAtDate("2022-10-28");
    assertEquals(m.toString(), check.toString());
  }

  @Test
  public void testGetValuationAtDateForThreeStockPortfolio() {
    int qty1 = 25;
    double price1 = 155.74;
    double temp1 = price1 * qty1;

    stocks.put("tsla", 25);
    stocks.put("amzn", 10);
    Portfolio p2 = new PortfolioImpl(name, stocks);
    int qty2 = 25;
    double price2 = 228.52;
    double temp2 = price2 * qty2;

    int qty3 = 10;
    double price3 = 103.41;
    double temp3 = price3 * qty3;

    Map<String, Double> check = new HashMap<>();
    check.put("aapl", temp1);
    check.put("tsla", temp2);
    check.put("amzn", temp3);
    Map<String, Double> m = p2.getValuationAtDate("2022-10-28");
    assertEquals(m.toString(), check.toString());
  }

  @Test
  public void testGetName() {
    assertEquals(name, p.getName());
  }

  @Test
  public void testGetStocks() {
    Map<String, Integer> check = new HashMap<>();
    check.put("aapl", 25);
    assertEquals(check, p.getStocks());
  }
}