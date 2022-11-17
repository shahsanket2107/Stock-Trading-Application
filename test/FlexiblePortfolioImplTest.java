import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.FlexiblePortfolio;
import model.FlexiblePortfolioImpl;
import model.Stocks;
import model.StocksImpl;

import static org.junit.Assert.assertEquals;

/**
 * This test class is used to test the Flexible Portfolio model. It has methods that verifies that
 * getters and setters for the list of stocks work correctly.
 */
public class FlexiblePortfolioImplTest {

  private FlexiblePortfolio fp;
  private List<Stocks> s;

  @Before
  public void setup() {
    s = new ArrayList<>();
    s.add(new StocksImpl("2022-10-28", "aapl", 20));
    s.add(new StocksImpl("222-10-25", "tsla", 2));
    fp = new FlexiblePortfolioImpl("test_portfolio_1", s);
  }

  @Test
  public void testGetName() {
    assertEquals("test_portfolio_1", fp.getName());
  }

  @Test
  public void getStocks() {
    assertEquals(s, fp.getStocks());
  }

  @Test
  public void setStocks() {
    s.add(new StocksImpl("2022-10-26", "zs", 12));
    fp.setStocks(s);
    assertEquals(s, fp.getStocks());
  }
}