import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import model.UserModelExtension;
import model.UserModelExtensionImpl;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

/**
 * In this test class we test the methods like create new portfolio with dollar cost averaging,
 * invest a certain amount to an existing portfolio and apply dollar cost strategy to an existing
 * portfolio. We test all these methods with correct as well as incorrect inputs and verify their
 * behavior.
 */
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
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 1737.4\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("sanket_p1", "2022-11-19",
            2000, m, 20);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio sanket_p1 on 2022-11-19 is:-\n" +
            "{\n" +
            "  \"MSFT\" : 0.8180128072712249,\n" +
            "  \"AAPL\" : 12.357610972231607,\n" +
            "  \"TSLA\" : 11.179484124620242,\n" +
            "  \"ZS\" : 18.64184814596822\n" +
            "}", user.getFlexiblePortfolioComposition("sanket_p1", "2022-11-19")
            .toString().replace("\r", ""));
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 3737.4\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "The stock valuation breakdown is: \n" +
                    "MSFT : $ 200.43767816566825\n" +
                    "AAPL : $ 1855.8660158097427\n" +
                    "TSLA : $ 1899.5061476142253\n" +
                    "ZS : $ 2549.4591524426132\n" +
                    "Portfolio_Valuation at 2022-11-22 is : $ 6505.26899403225\n",
            user.getFlexiblePortfolioTotalValuation("2022-11-22", "sanket_p1")
                    .toString().replace("\r", ""));
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
    boolean test = user.investFractionalPercentage("sanket_p1", "19-11-2022",
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
    boolean test = user.investFractionalPercentage("sanket_p1", "2023-11-19",
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
    boolean test = user.investFractionalPercentage("sanket_p1", "2022-11-19",
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
    boolean test = user.investFractionalPercentage("sanket_p1", "2022-11-19",
            1000, m, -20);
  }

  @Test
  public void investFractionalPercentageWithNoCommissionFees() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 1737.4\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.investFractionalPercentage("sanket_p1", "2022-11-19",
            2000, m, 0);
    assertEquals(true, test);
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 3737.4\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
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
    assertEquals("Portfolio_Name: test_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 36000.0\n",
            user.getCostBasis("2022-11-19", "test_p1")
                    .toString().replace("\r", ""));
  }

  @Test
  public void testCreateDollarCostAveragePortfolioWithFutureEndDate()
          throws IOException, ParseException {
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
    assertEquals("Portfolio_Name: test_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 36000.0\n",
            user.getCostBasis("2022-11-19", "test_p1")
                    .toString().replace("\r", ""));

    String fileName = "user_persistance.json";
    FileReader reader = new FileReader(fileName);
    JSONParser parser = new JSONParser();
    JSONObject result = (JSONObject) parser.parse(reader);
    JSONArray values = (JSONArray) result.get("test_p1");
    assertNotNull(values);
  }

  @Test
  public void testCreateDollarCostAveragePortfolioWithNoEndDate()
          throws IOException, ParseException {
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
    assertEquals("Portfolio_Name: test_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 36000.0\n",
            user.getCostBasis("2022-11-19", "test_p1")
                    .toString().replace("\r", ""));
    String fileName = "user_persistance.json";
    FileReader reader = new FileReader(fileName);
    JSONParser parser = new JSONParser();
    JSONObject result = (JSONObject) parser.parse(reader);
    JSONArray values = (JSONArray) result.get("test_p1");
    assertNotNull(values);
  }

  @Test
  public void testCreateDollarCostAveragePortfolioOnMultipleDates() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2022-11-19", 30);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio test_p1 on 2021-07-19 is:-\n" +
                    "{\n" +
                    "  \"MSFT\" : 1.4686879880390267,\n" +
                    "  \"AAPL\" : 15.876080161794448,\n" +
                    "  \"TSLA\" : 0.6253250890523416,\n" +
                    "  \"ZS\" : 4.506956391645588\n" +
                    "}",
            user.getFlexiblePortfolioComposition("test_p1", "2021-07-19")
                    .toString().replace("\r", ""));

    assertEquals("Portfolio_Name: test_p1\n" +
                    "Cost basis of your portfolio at 2021-07-19 is : $ 4000.0\n",
            user.getCostBasis("2021-07-19", "test_p1")
                    .toString().replace("\r", ""));

    assertEquals("Portfolio_Name: test_p1\n" +
                    "Cost basis of your portfolio at 2022-10-19 is : $ 34000.0\n",
            user.getCostBasis("2022-10-19", "test_p1")
                    .toString().replace("\r", ""));

    assertEquals("Portfolio_Name: test_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 36000.0\n",
            user.getCostBasis("2022-11-19", "test_p1")
                    .toString().replace("\r", ""));
  }

  @Test
  public void testValuationCreateDollarCostAveragePortfolioOnMultipleDates() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 10.0);
    m.put("MSFT", 10.0);
    boolean test = user.dollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2022-11-19", 30);
    assertEquals(true, test);
    assertEquals("The portfolio composition of portfolio test_p1 on 2021-07-19 is:-\n" +
                    "{\n" +
                    "  \"MSFT\" : 1.4686879880390267,\n" +
                    "  \"AAPL\" : 15.876080161794448,\n" +
                    "  \"TSLA\" : 0.6253250890523416,\n" +
                    "  \"ZS\" : 4.506956391645588\n" +
                    "}",
            user.getFlexiblePortfolioComposition("test_p1", "2021-07-19")
                    .toString().replace("\r", ""));

    assertEquals("Portfolio_Name: test_p1\n" +
                    "The stock valuation breakdown is: \n" +
                    "MSFT : $ 406.8412595666908\n" +
                    "AAPL : $ 2261.547619047619\n" +
                    "TSLA : $ 404.0975790474042\n" +
                    "ZS : $ 1005.0963449008825\n" +
                    "Portfolio_Valuation at 2021-07-19 is : $ 4077.582802562596\n",
            user.getFlexiblePortfolioTotalValuation("2021-07-19", "test_p1")
                    .toString().replace("\r", ""));

    assertEquals("Portfolio_Name: test_p1\n" +
                    "The stock valuation breakdown is: \n" +
                    "MSFT : $ 2835.7260625670738\n" +
                    "AAPL : $ 17531.232710343564\n" +
                    "TSLA : $ 1171.844950535491\n" +
                    "ZS : $ 6013.365078442779\n" +
                    "Portfolio_Valuation at 2022-10-19 is : $ 27552.16880188891\n",
            user.getFlexiblePortfolioTotalValuation("2022-10-19", "test_p1")
                    .toString().replace("\r", ""));

    assertEquals("Portfolio_Name: test_p1\n" +
                    "The stock valuation breakdown is: \n" +
                    "MSFT : $ 3096.463766900373\n" +
                    "AAPL : $ 19113.67079346399\n" +
                    "TSLA : $ 1055.5646567457309\n" +
                    "ZS : $ 5967.133539120163\n" +
                    "Portfolio_Valuation at 2022-11-21 is : $ 29232.83275623025\n",
            user.getFlexiblePortfolioTotalValuation("2022-11-21", "test_p1")
                    .toString().replace("\r", ""));
  }

  @Test
  public void testExistingDollarCostAveragePortfolioWithoutFutureDate() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 1737.4\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
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
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 13737.399999999996\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
  }

  @Test
  public void testExistingDollarCostAveragePortfolioWithFutureDate()
          throws IOException, ParseException {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 1737.4\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
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
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 13737.399999999996\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
    String fileName = "user_persistance.json";
    FileReader reader = new FileReader(fileName);
    JSONParser parser = new JSONParser();
    JSONObject result = (JSONObject) parser.parse(reader);
    JSONArray values = (JSONArray) result.get("sanket_p1");
    assertNotNull(values);
  }

  @Test
  public void testExistingDollarCostAveragePortfolioWithNoEndDate()
          throws IOException, ParseException {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 1737.4\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
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
    assertEquals("Portfolio_Name: sanket_p1\n" +
                    "Cost basis of your portfolio at 2022-11-19 is : $ 13737.399999999996\n",
            user.getCostBasis("2022-11-19", "sanket_p1")
                    .toString().replace("\r", ""));
    String fileName = "user_persistance.json";
    FileReader reader = new FileReader(fileName);
    JSONParser parser = new JSONParser();
    JSONObject result = (JSONObject) parser.parse(reader);
    JSONArray values = (JSONArray) result.get("sanket_p1");
    assertNotNull(values);
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