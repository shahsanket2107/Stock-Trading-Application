import org.junit.Test;

import model.Stocks;
import model.StocksImpl;

import static org.junit.Assert.assertEquals;

/**
 * In this test class we test the StocksImpl model. getValuationFromDate method is tested by
 * passing in static values of quantity, ticker and date for valid as well as invalid inputs.
 */
public class StocksImplTest {

  /**
   * In this test we assume and test the valuation of 25 shares of apple on 28th October 2022.
   * The closing value of Apple share on 28th October is $155.74.
   */
  @Test
  public void testGetValuationFromDate28Oct() {
    Stocks s = new StocksImpl("AAPL");
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
    Stocks s = new StocksImpl("tsla");
    int qty = 25;
    double price = 227.54;
    double ans = price * qty;
    assertEquals(ans, s.getValuationFromDate(25, "2022-10-31"), 0.00);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidTicker() {
    Stocks s = new StocksImpl("tony");
    int qty = 25;
    double price = 155.74;
    double ans = price * qty;
    assertEquals(ans, s.getValuationFromDate(25, "2022-10-28"), 0.00);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidFutureDate() {
    Stocks s = new StocksImpl("AAPL");
    int qty = 25;
    double price = 155.74;
    double ans = price * qty;
    assertEquals(ans, s.getValuationFromDate(25, "2022-12-3"), 0.00);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidWeekendDate() {
    Stocks s = new StocksImpl("AAPL");
    int qty = 25;
    double price = 155.74;
    double ans = price * qty;
    assertEquals(ans, s.getValuationFromDate(25, "2022-10-29"), 0.00);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidDateFormat() {
    Stocks s = new StocksImpl("AAPL");
    int qty = 25;
    double price = 155.74;
    double ans = price * qty;
    assertEquals(ans, s.getValuationFromDate(25, "21-10-2022"), 0.00);
  }
}