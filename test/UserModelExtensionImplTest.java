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
  }

  @Test
  public void investFractionalPercentage() {
    user.loadFlexiblePortfolio("sanket_portfolios.json");
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "sanket_p1\n", user.getPortfoliosName().toString());
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    String test = user.investFractionalPercentage("sanket_p1", "2022-11-19",
            2000, m, 20);
    assertEquals("Amount Invested Successfully!!", test);
  }

  @Test
  public void testCreateDollarCostAveragePortfolio() {
    Map<String, Double> m = new HashMap();
    m.put("AAPL", 55.0);
    m.put("ZS", 25.0);
    m.put("TSLA", 20.0);
    String test = user.createDollarCostAveragingPortfolio("test_p1", m, 2000,
            20, "2021-06-19", "2022-11-19", 30);
    assertEquals("Dollar Cost Averaging Portfolio Created Successfully!!", test);
    assertEquals("", user.getFlexiblePortfolioComposition("test_p1", "2022-11-19"));
  }


}