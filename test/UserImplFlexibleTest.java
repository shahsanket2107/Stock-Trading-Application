import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import model.Stocks;
import model.StocksImpl;
import model.User;
import model.UserImpl;

import static org.junit.Assert.assertEquals;

/**
 * This test class extends the UserImpl test which contains all the tests for Users with
 * inflexible portfolio. For users having flexible portfolios all the new tests are written in
 * this class.
 * In this test class we test the UserImpl model. We test various methods that are used by the user
 * like createPortfolio,loadPortfolio, getPortfolioNames, getPortfolioComposition etc. For invalid
 * this class only tests those invalid inputs that are handled in this UserImpl class. Some invalid
 * inputs are invalidated at view so those are tested there.
 */
public class UserImplFlexibleTest extends UserImplTest {

  private User user;

  @Before
  public void setup() {
    super.setup();
    List<Stocks> s = new ArrayList<>();
    s.add(new StocksImpl("2022-10-28", "tsla", 23));
    s.add(new StocksImpl("2022-11-02", "aapl", 10));


    List<Stocks> s2 = new ArrayList<>();
    s2.add(new StocksImpl("2022-10-24", "zs", 12));
    s2.add(new StocksImpl("2022-10-25", "msft", 8));
    s2.add(new StocksImpl("2022-10-28", "amd", 33));


    user = new UserImpl("test_user", new ArrayList<>(), new ArrayList<>());
    user.createFlexiblePortfolio("p1", s);
    user.createFlexiblePortfolio("p2", s2);
  }

  @Test
  public void testGetPortfoliosName() {
    assertEquals("The list of portfolios is:\n" +
            "p1\n" +
            "p2\n", user.getPortfoliosName().toString());
  }

  @Test
  public void testCheckPortfolioExists() {
    assertEquals(0, user.checkPortfolioExists("test"));
    assertEquals(2, user.checkPortfolioExists("p1"));
    assertEquals(2, user.checkPortfolioExists("p2"));
  }

  @Test
  public void testCreateFlexiblePortfolio() {
    User u = new UserImpl();
    List<Stocks> s = new ArrayList<>();
    s.add(new StocksImpl("2022-10-28", "tsla", 23));
    s.add(new StocksImpl("2022-11-02", "aapl", 10));

    List<Stocks> s2 = new ArrayList<>();
    s2.add(new StocksImpl("2022-10-24", "zs", 12));
    s2.add(new StocksImpl("2022-10-25", "msft", 8));

    u.createFlexiblePortfolio("test_p1", s);
    u.createFlexiblePortfolio("test_p2", s2);

    assertEquals("The list of portfolios is:\n" +
            "test_p1\n" +
            "test_p2\n", u.getPortfoliosName().toString());
  }

  @Test
  public void testGetFlexiblePortfolioComposition() {
    assertEquals("The portfolio composition of portfolio p1 on 2022-10-31 is:-\n" +
                    "{\n" +
                    "  \"tsla\" : 23\n" +
                    "}".toString().replace("\r", ""),
            user.getFlexiblePortfolioComposition("p1", "2022-10-31")
                    .toString().replace("\r", ""));

    assertEquals("The portfolio composition of portfolio p1 on 2022-11-10 is:-\n" +
                    "{\n" +
                    "  \"aapl\" : 10,\n" +
                    "  \"tsla\" : 23\n" +
                    "}",
            user.getFlexiblePortfolioComposition("p1", "2022-11-10")
                    .toString().replace("\r", ""));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLoadFlexiblePortfolioForDifferentExtension() {
    user.loadFlexiblePortfolio("file.xml");
  }

  @Test(expected = IllegalArgumentException.class)
  public void testInvalidLoadFlexiblePortfolioForFileNotExists() {
    user.loadFlexiblePortfolio("file.json");
  }

  @Test
  public void testLoadFlexiblePortfolio() {
    User u1 = new UserImpl();
    u1.loadFlexiblePortfolio("sanket_portfolios.json");
    assertEquals("The list of portfolios is:\n" +
            "p1\n", u1.getPortfoliosName().toString());
  }

  @Test
  public void testBuyStocksOnFutureDatePortfolio() {
    String check = user.buyStocks("goog", 20, "p1", "2023-10-28");
    assertEquals("Date cannot be greater or equal to current date. Try a different date",
            check);
  }

  @Test
  public void testValidBuyStocks() {
    String check = user.buyStocks("amd", 20, "p1", "2022-10-28");
    assertEquals("Stocks bought successfully and added to your portfolio!", check);
    assertEquals("The portfolio composition of portfolio p1 on 2022-10-28 is:-\n" +
            "{\n" +
            "  \"tsla\" : 23,\n" +
            "  \"amd\" : 20\n" +
            "}", user.getFlexiblePortfolioComposition("p1", "2022-10-28")
            .toString().replace("\r", ""));

    String check2 = user.buyStocks("amd", 20, "p1", "2022-10-27");
    assertEquals("Stocks bought successfully and added to your portfolio!", check2);
    assertEquals("The portfolio composition of portfolio p1 on 2022-10-31 is:-\n" +
            "{\n" +
            "  \"tsla\" : 23,\n" +
            "  \"amd\" : 40\n" +
            "}", user.getFlexiblePortfolioComposition("p1", "2022-10-31")
            .toString().replace("\r", ""));
  }

  @Test
  public void testSellingStockDoesNotExist() {
    String check = user.sellStocks("zs", 10, "p1", "2022-10-28");
    assertEquals("The entered stock does not exist in your portfolio!", check);
  }

  @Test
  public void testSellingStockDoesNotHaveEnoughQty() {
    String check = user.sellStocks("aapl", 40, "p1", "2022-10-28");
    assertEquals("Quantity entered is more than what you have in your portfolio!", check);
  }

  @Test
  public void testValidSellStocks() {
    String check = user.sellStocks("tsla", 20, "p1", "2022-10-31");
    assertEquals("Stocks sold successfully!", check);
    assertEquals("The portfolio composition of portfolio p1 on 2022-11-01 is:-\n" +
            "{\n" +
            "  \"tsla\" : 3\n" +
            "}", user.getFlexiblePortfolioComposition("p1", "2022-11-01")
            .toString().replace("\r", ""));
  }

  @Test
  public void testGetFlexiblePortfolioTotalValuationOnAWeekend() {
    StringBuilder check = user.getFlexiblePortfolioTotalValuation("2022-11-05", "p1");
    assertEquals("Stock market is closed at the date: 2022-11-05. So please enter a " +
            "different date\n", check.toString());
  }

  @Test
  public void testGetValidFlexiblePortfolioValuation() {
    StringBuilder check = user.getFlexiblePortfolioTotalValuation("2022-11-07", "p1");
    assertEquals("Portfolio_Name: p1\n" +
            "The stock valuation breakdown is: \n" +
            "aapl : $ 1389.1999999999998\n" +
            "tsla : $ 4532.84\n" +
            "Portfolio_Valuation at 2022-11-07 is : $ 5922.04\n", check.toString());

    StringBuilder check2 = user.getFlexiblePortfolioTotalValuation("2022-11-07", "p2");
    assertEquals("Portfolio_Name: p2\n" +
            "The stock valuation breakdown is: \n" +
            "msft : $ 1822.96\n" +
            "zs : $ 1408.32\n" +
            "amd : $ 2081.64\n" +
            "Portfolio_Valuation at 2022-11-07 is : $ 5312.92\n", check2.toString());

    StringBuilder check3 = user.getFlexiblePortfolioTotalValuation("2020-11-04", "p2");
    assertEquals("Portfolio_Valuation at 2020-11-04 is : $ 0.0\n", check3.toString());

    StringBuilder check4 = user.getFlexiblePortfolioTotalValuation("2022-10-27", "p2");
    assertEquals("Portfolio_Name: p2\n" +
            "The stock valuation breakdown is: \n" +
            "msft : $ 1814.0\n" +
            "zs : $ 1845.2400000000002\n" +
            "Portfolio_Valuation at 2022-10-27 is : $ 3659.2400000000002\n", check4.toString());
  }

  @Test
  public void testValidateDateAccToApi() {
    assertEquals(true, user.validateDateAccToApi("aapl", "2022-10-28"));
    assertEquals(false, user.validateDateAccToApi("aapl", "2022-10-30"));
  }

  @Test
  public void testInvalidGetCostBasis() {
    assertEquals("Given portfolio doesn't exist!!",
            user.getCostBasis("2022-10-28", "test").toString());
  }

  @Test
  public void testValidGetCostBasis() {
    assertEquals("Portfolio_Name: p1\n" +
                    "Cost basis of your portfolio at 2022-10-28 is : $ 5259.29\n",
            user.getCostBasis("2022-10-28", "p1").toString());
    String check = user.sellStocks("tsla", 10, "p1", "2022-10-31");
    assertEquals("Stocks sold successfully!", check);
    assertEquals("Portfolio_Name: p1\n" +
                    "Cost basis of your portfolio at 2022-11-01 is : $ 5262.62\n",
            user.getCostBasis("2022-11-01", "p1").toString());
    String check2 = user.buyStocks("zs", 2, "p1", "2022-11-03");
    assertEquals("Stocks bought successfully and added to your portfolio!", check2);
    assertEquals("Portfolio_Name: p1\n" +
                    "Cost basis of your portfolio at 2022-11-04 is : $ 6981.72\n",
            user.getCostBasis("2022-11-04", "p1").toString());
  }

  @Test
  public void testDisplayChartInvalidDate() {
    assertEquals("Start date cannot be more than end date!",
            user.displayChart("2022-10-28", "2022-8-28", "p1").toString());
  }

  @Test
  public void testDisplayChartValidDateGroupByDays() {
    List<Stocks> s = new ArrayList<>();
    s.add(new StocksImpl("2022-10-20", "zs", 12));
    s.add(new StocksImpl("2022-10-25", "msft", 8));
    s.add(new StocksImpl("2022-10-28", "amd", 33));
    User user = new UserImpl("user", new ArrayList<>(), new ArrayList<>());
    user.createFlexiblePortfolio("chart_p", s);
    user.sellStocks("zs", 10, "chart_p", "2022-11-02");
    assertEquals("Performance of portfolio chart_p from 2022-10-20 to 2022-11-05\n" +
                    "2022-10-20: *****\n" +
                    "2022-10-21: *****\n" +
                    "2022-10-22: ****\n" +
                    "2022-10-23: ****\n" +
                    "2022-10-24: ****\n" +
                    "2022-10-25: **********\n" +
                    "2022-10-26: *********\n" +
                    "2022-10-27: *********\n" +
                    "2022-10-28: **************\n" +
                    "2022-10-29: **************\n" +
                    "2022-10-30: **************\n" +
                    "2022-10-31: **************\n" +
                    "2022-11-01: **************\n" +
                    "2022-11-02: **********\n" +
                    "2022-11-03: **********\n" +
                    "2022-11-04: **********\n" +
                    "2022-11-05: **********\n" +
                    "Scale: * = $399",
            user.displayChart("2022-10-20", "2022-11-05", "chart_p").toString());
  }

  @Test
  public void testDisplayChartValidDateGroupByWeeks() {
    List<Stocks> s = new ArrayList<>();
    s.add(new StocksImpl("2022-07-20", "zs", 12));
    s.add(new StocksImpl("2022-08-17", "msft", 8));
    s.add(new StocksImpl("2022-10-28", "amd", 33));
    User user = new UserImpl("user", new ArrayList<>(), new ArrayList<>());
    user.createFlexiblePortfolio("chart_p", s);
    user.sellStocks("zs", 10, "chart_p", "2022-11-02");
    assertEquals("Performance of portfolio chart_p from 2022-06-20 to 2022-11-05\n" +
                    "2022-06-20: \n" +
                    "2022-06-27: \n" +
                    "2022-07-04: \n" +
                    "2022-07-11: \n" +
                    "2022-07-18: \n" +
                    "2022-07-25: ****\n" +
                    "2022-08-01: ****\n" +
                    "2022-08-08: *****\n" +
                    "2022-08-15: *****\n" +
                    "2022-08-22: *********\n" +
                    "2022-08-29: *********\n" +
                    "2022-09-05: ********\n" +
                    "2022-09-12: **********\n" +
                    "2022-09-19: *********\n" +
                    "2022-09-26: *********\n" +
                    "2022-10-03: *********\n" +
                    "2022-10-10: ********\n" +
                    "2022-10-17: ********\n" +
                    "2022-10-24: ********\n" +
                    "Scale: * = $445",
            user.displayChart("2022-06-20", "2022-11-05", "chart_p").toString());
  }

  @Test
  public void testDisplayChartValidDateGroupByMonths() {
    List<Stocks> s = new ArrayList<>();
    s.add(new StocksImpl("2021-01-20", "zs", 12));
    s.add(new StocksImpl("2022-08-17", "msft", 8));
    s.add(new StocksImpl("2022-10-28", "amd", 33));
    User user = new UserImpl("user", new ArrayList<>(), new ArrayList<>());
    user.createFlexiblePortfolio("chart_p", s);
    user.sellStocks("zs", 10, "chart_p", "2022-11-02");
    assertEquals("Performance of portfolio chart_p from 2021-01-20 to 2022-11-05\n" +
                    "Jan 2021: ******\n" +
                    "Feb 2021: ******\n" +
                    "Mar 2021: *****\n" +
                    "Apr 2021: ******\n" +
                    "May 2021: ******\n" +
                    "Jun 2021: *******\n" +
                    "Jul 2021: *******\n" +
                    "Aug 2021: *********\n" +
                    "Sep 2021: ********\n" +
                    "Oct 2021: **********\n" +
                    "Nov 2021: ***********\n" +
                    "Dec 2021: **********\n" +
                    "Jan 2022: ********\n" +
                    "Feb 2022: *******\n" +
                    "Mar 2022: *******\n" +
                    "Apr 2022: ******\n" +
                    "May 2022: *****\n" +
                    "Jun 2022: *****\n" +
                    "Jul 2022: *****\n" +
                    "Aug 2022: **********\n" +
                    "Sep 2022: **********\n" +
                    "Oct 2022: ***************\n" +
                    "Nov 2022: ***********\n" +
                    "Scale: * = $389",
            user.displayChart("2021-01-20", "2022-11-05", "chart_p").toString());
  }

  @Test
  public void testDisplayChartValidDateGroupByYears() {
    List<Stocks> s = new ArrayList<>();
    s.add(new StocksImpl("2020-02-20", "zs", 12));
    s.add(new StocksImpl("2000-08-17", "msft", 850));
    s.add(new StocksImpl("2022-10-28", "amd", 33));
    User user = new UserImpl("user", new ArrayList<>(), new ArrayList<>());
    user.createFlexiblePortfolio("chart_p", s);
    user.sellStocks("zs", 10, "chart_p", "2022-11-02");
    assertEquals("Performance of portfolio chart_p from 2000-01-20 to 2022-11-05\n" +
                    "2000: ****\n" +
                    "2001: ****\n" +
                    "2002: ****\n" +
                    "2003: ****\n" +
                    "2004: ****\n" +
                    "2005: ****\n" +
                    "2006: ****\n" +
                    "2007: ****\n" +
                    "2008: ****\n" +
                    "2009: ****\n" +
                    "2010: ****\n" +
                    "2011: ****\n" +
                    "2012: ****\n" +
                    "2013: ****\n" +
                    "2014: ****\n" +
                    "2015: ****\n" +
                    "2016: ****\n" +
                    "2017: ****\n" +
                    "2018: ****\n" +
                    "2019: ******\n" +
                    "2020: *********\n" +
                    "2021: **************\n" +
                    "2022: *********\n" +
                    "Scale: * = $20931",
            user.displayChart("2000-01-20", "2022-11-05", "chart_p").toString());
  }
}