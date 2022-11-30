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

  @Test(expected = IllegalArgumentException.class)
  public void investFractionalPercentageWithNegativeAmount() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("testPortfolio", "2022-11-19",
            -1000, m, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void investFractionalPercentageWithNegativeCommissionFees() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("testPortfolio", "2022-11-19",
            1000, m, -20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void investFractionalPercentageWithFractionalSharesLessThanHundred() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 5.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("testPortfolio", "2022-11-19",
            1000, m, 20);
  }

  @Test(expected = IllegalArgumentException.class)
  public void investFractionalPercentageWithFractionalSharesGreaterThanHundred() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 45.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("testPortfolio", "2022-11-19",
            1000, m, 20);
  }

  @Test
  public void testCreateDollarCostAveragePortfolioWithoutFutureEndDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2022-11-19", 30);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio test_p1 on 2022-11-19 is:-\n" +
                    "{\n" +
                    "  \"MSFT\" : 12.792661709978818,\n" +
                    "  \"AAPL\" : 129.13769875997562,\n" +
                    "  \"TSLA\" : 6.287988662332346,\n" +
                    "  \"ZS\" : 43.90180649735259\n" +
                    "}",
            user.getFlexiblePortfolioComposition("test_p1", "2022-11-19")
                    .toString().replace("\r", ""));
  }

  @Test
  public void testCreateDollarCostAveragePortfolioWithFutureEndDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2023-11-19", 30);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio test_p1 on 2022-11-19 is:-\n" +
                    "{\n" +
                    "  \"MSFT\" : 12.792661709978818,\n" +
                    "  \"AAPL\" : 129.13769875997562,\n" +
                    "  \"TSLA\" : 6.287988662332346,\n" +
                    "  \"ZS\" : 43.90180649735259\n" +
                    "}",
            user.getFlexiblePortfolioComposition("test_p1", "2022-11-19")
                    .toString().replace("\r", ""));
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
    assertEquals("The portfolio composition of portfolio test_p1 on 2022-11-19 is:-\n" +
            "{\n" +
            "  \"MSFT\" : 12.792661709978818,\n" +
            "  \"AAPL\" : 129.13769875997562,\n" +
            "  \"TSLA\" : 6.287988662332346,\n" +
            "  \"ZS\" : 43.90180649735259\n" +
            "}", user.getFlexiblePortfolioComposition("test_p1", "2022-11-19")
            .toString().replace("\r", ""));
  }

  @Test
  public void testExistingDollarCostAveragePortfolioWithoutFutureDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    boolean test = user.dollarCostAveragingPortfolio("sanket_p1", m, 2000,
            20, "2022-06-19", "2022-11-19", 30);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio sanket_p1 on 2022-11-19 is:-\n" +
            "{\n" +
            "  \"AAPL\" : 48.495596260544055,\n" +
            "  \"TSLA\" : 16.735469413477954,\n" +
            "  \"ZS\" : 34.0640629821031\n" +
            "}", user.getFlexiblePortfolioComposition("sanket_p1", "2022-11-19")
            .toString().replace("\r", ""));
  }

  @Test
  public void testExistingDollarCostAveragePortfolioWithFutureDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    boolean test = user.dollarCostAveragingPortfolio("sanket_p1", m, 2000,
            20, "2022-06-19", "2023-11-19", 30);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio sanket_p1 on 2022-11-19 is:-\n" +
            "{\n" +
            "  \"AAPL\" : 48.495596260544055,\n" +
            "  \"TSLA\" : 16.735469413477954,\n" +
            "  \"ZS\" : 34.0640629821031\n" +
            "}", user.getFlexiblePortfolioComposition("sanket_p1", "2022-11-19")
            .toString().replace("\r", ""));
  }

  @Test
  public void testExistingDollarCostAveragePortfolioWithNoEndDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    boolean test = user.dollarCostAveragingPortfolio("sanket_p1", m, 2000,
            20, "2022-06-19", "", 30);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio sanket_p1 on 2022-11-19 is:-\n" +
            "{\n" +
            "  \"AAPL\" : 48.495596260544055,\n" +
            "  \"TSLA\" : 16.735469413477954,\n" +
            "  \"ZS\" : 34.0640629821031\n" +
            "}", user.getFlexiblePortfolioComposition("sanket_p1", "2022-11-19")
            .toString().replace("\r", ""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateDollarCostAveragePortfolioWithNegativeAmount() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, -2000,
            20, "2021-06-19", "2022-11-19", 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateDollarCostAveragePortfolioWithNegativeCommissionFees() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            -20, "2021-06-19", "2022-11-19", 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateDollarCostAveragePortfolioWithNegativeInterval() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2022-11-19", -3);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateDollarCostAveragePortfolioWithUnformattedStartDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "19-06-2022", "2022-11-19", 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateDollarCostAveragePortfolioWithUnformattedEndDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2022-06-19", "19-11-2022", 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateDollarCostAveragePortfolioWithFractionalShareSumLessThanHundred() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 5.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2022-11-19", 30);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testCreateDollarCostAveragePortfolioWithFractionalShareSumMoreThanHundred() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 35.0);
    m.put("TSLA", 20.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2022-11-19", 30);
  }

  @Test
  public void testLoadFutureStrategy() {
    boolean check = user.loadPersistantStrategy("sanket_p1");
    boolean check2 = user.loadPersistantStrategy("test_p1");
    assertEquals(false, check);
    assertEquals(false, check2);
  }

}