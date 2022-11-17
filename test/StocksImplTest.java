import org.junit.Before;
import org.junit.Test;

import model.Stocks;
import model.StocksImpl;

import static org.junit.Assert.assertEquals;

/**
 * In this test class we test the StocksImpl model. getValuationFromDate method is tested by
 * passing in static values of quantity, ticker and date for valid as well as invalid inputs.
 * In part two of assignment we test getters and setters for various stock parameters like date,
 * quantity and ticker.
 */
public class StocksImplTest {
  private Stocks s;
  private Stocks stocks;

  @Before
  public void setup() {
    s = new StocksImpl("AAPL");
    stocks = new StocksImpl("2022-10-29", "tsla", 23);
  }

  /**
   * In this test we assume and test the valuation of 25 shares of apple on 28th October 2022.
   * The closing value of Apple share on 28th October is $155.74.
   */
  @Test
  public void testGetValuationFromDate28Oct() {
    int qty = 25;
    double price = 155.74;
    double ans = price * qty;
    assertEquals(ans, s.getValuationFromDate(25, "2022-10-28"), 0.00);
  }

  /**
   * In this test we assume and test the valuation of 25 shares of tsla on 28th October 2022.
   * The closing value of Apple share on 28th October is $227.54.
   */
  @Test
  public void testGetValuationFromDate31Oct() {
    Stocks s1 = new StocksImpl("tsla");
    int qty = 25;
    double price = 227.54;
    double ans = price * qty;
    assertEquals(ans, s1.getValuationFromDate(25, "2022-10-31"), 0.00);
  }

  @Test
  public void testGetQty() {
    assertEquals(23, stocks.getQty());
  }

  @Test
  public void testSetQty() {
    stocks.setQty(45);
    assertEquals(45, stocks.getQty());
  }

  @Test
  public void testGetDate() {
    assertEquals("2022-10-29", stocks.getDate());
  }

  @Test
  public void testGetTicker() {
    assertEquals("AAPL", s.getTicker());
    assertEquals("tsla", stocks.getTicker());
  }

  @Test
  public void testGetCostBasis() {
    assertEquals(0.0, stocks.getCostBasis(), 0.0f);
  }

  @Test
  public void testSetCostBasis() {
    stocks.setCostBasis(23.5);
    assertEquals(23.5, stocks.getCostBasis(), 0.0f);
  }


}