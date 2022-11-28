import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import model.UserModelExtension;
import model.UserModelExtensionImpl;

import static org.junit.Assert.assertEquals;

public class UserModelExtensionImplTest {

  private UserModelExtension user;

  @Before
  public void setup() {
    user = new UserModelExtensionImpl();
    user.setName("test_11");
    user.loadFlexiblePortfolio("sanket_portfolios.json");
  }

  @Test
  public void investFractionalPercentage() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("sanket_p1", "2022-11-19",
            2000, m, 20);
    assertEquals(true, test);
  }

  @Test(expected = IllegalArgumentException.class)
  public void investFractionalPercentageToANonExistingPortfolio() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("testPortfolio", "2022-11-19",
            2000, m, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void investFractionalPercentageWithInvalidDateFormat() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("testPortfolio", "19-11-2022",
            2000, m, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void investFractionalPercentageWithFutureDate() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("testPortfolio", "2023-11-19",
            2000, m, 20);
  }

  @Test
  public void testCreateDollarCostAveragePortfolio() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2023-11-19", 30);
    assertEquals(true, test);
    //assertEquals("", user.getFlexiblePortfolioComposition("test_p1", "2022-11-19"));
  }

  @Test
  public void testCreateDollarCostAveragePortfolioWithNoEndDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "", 30);
    assertEquals(true, test);
    //assertEquals("", user.getFlexiblePortfolioComposition("test_p1", "2022-11-19"));
  }

  @Test
  public void testExistingDollarCostAveragePortfolio() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    boolean test = user.dollarCostAveragingPortfolio("sanket_p1", m, 2000,
            20, "2022-06-19", "2022-11-19", 30);
    assertEquals(true, test);
    assertEquals("", user.getFlexiblePortfolioComposition("sanket_p1", "2022-11-19"));
  }


  @Test
  public void testLoadFutureStrategy() {
    boolean check = user.loadPersistantStrategy("sanket_p1");
    boolean check2 = user.loadPersistantStrategy("test_p1");
    assertEquals(false, check);
    assertEquals(false, check2);
  }

}