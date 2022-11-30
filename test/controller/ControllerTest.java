package controller;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import junit.framework.TestCase;
import model.UserModelExtensionImpl;
import org.junit.Before;
import org.junit.Test;
import view.IView;

public class ControllerTest extends TestCase {

  private PrintStream out;
  private ByteArrayOutputStream b;
  private Controller controller;
  private UserModelExtensionImpl user;

  @Before
  public void setUp() {
    user = new UserModelExtensionImpl();
    controller = new Controller(user, "Deadpool");
    IView view = new mockView();
    controller.setView(view);
    b = new ByteArrayOutputStream();
    out = new PrintStream(b);
  }

  @Test
  public void testCreatePortfolio() {
    controller.createPortfolio();
    assertEquals(b.toString(), "Portfolio created successfully!\r\n");
  }

  @Test
  public void testBuyStocks() {
    controller.createPortfolio();
    controller.buyStocks();
    assertEquals(b.toString(),
        "Portfolio created successfully!\r\nStocks bought successfully and added to your"
            + " portfolio!\r\n");
  }

  @Test
  public void testSellStocks() {
    controller.createPortfolio();
    controller.sellStocks();
    assertEquals(b.toString(),
        "Portfolio created successfully!\r\nStocks sold successfully!\r\n");
  }

  @Test
  public void testValuation() {
    controller.createPortfolio();
    controller.getValuation();
    String s = "Portfolio created successfully!\r\nPortfolio_Name: p1\r\n"
        + "The stock valuation breakdown is: \r\n"
        + "AAPL : $ 1472.7\r\n"
        + "Portfolio_Valuation at 2022-10-21 is : $ 1472.7\r\n\r\n";
    assertEquals(b.toString().replace("\r", ""),
        s.replace("\r", ""));
  }

  @Test
  public void testCostBasis() {
    controller.createPortfolio();
    controller.getCostBasis();
    String s = "Portfolio created successfully!\n"
        + "Portfolio_Name: p1\n"
        + "Cost basis of your portfolio at 2022-10-21 is : $ 1475.0\r\n\r\n";
    assertEquals(b.toString().replace("\r", ""),
        s.replace("\r", ""));
  }

  @Test
  public void testComposition() {
    controller.createPortfolio();
    controller.getComposition();
    String s = "Portfolio created successfully!\n"
        + "The portfolio composition of portfolio p1 on 2022-10-21 is:-\n"
        + "{\n"
        + "  \"AAPL\" : 10.0\n"
        + "}\r\n";
    assertEquals(b.toString().replace("\r", ""),
        s.replace("\r", ""));
  }

  @Test
  public void testInvestment() {
    controller.createPortfolio();
    controller.investInPortfolio();
    String s = "Portfolio created successfully!\r\n"
        + "Amount Invested Successfully!!\r\n";
    assertEquals(b.toString().replace("\r", ""),
        s.replace("\r", ""));
  }

  @Test
  public void testDollarCost() {
    controller.createPortfolio();
    controller.createPortfolioUsingDollarCost();
    String s = "Portfolio created successfully!\r\n"
        + "Dollar Cost Averaging Portfolio Created Successfully!!\r\n";
    assertEquals(b.toString().replace("\r", ""),
        s.replace("\r", ""));
  }


  private class mockView implements IView {

    @Override
    public void addFeatures(Features features) {

    }

    @Override
    public void showOutput(String message) {
      out.println(message);
    }

    @Override
    public ArrayList<String> createPortfolioInput() throws IllegalArgumentException {
      ArrayList<String> input = new ArrayList<>();
      input.add("p1");
      input.add("aapl");
      input.add("10");
      input.add("2022-10-21");
      input.add("2.3");
      return input;
    }

    @Override
    public ArrayList<String> getInput() throws IllegalArgumentException {
      ArrayList<String> input = new ArrayList<>();
      input.add("p1");
      input.add("2022-10-21");
      return input;
    }

    @Override
    public ArrayList<String> getInputForPerformance() throws IllegalArgumentException {
      return null;
    }

    @Override
    public ArrayList<String> getInvestmentDetails() throws IllegalArgumentException {
      ArrayList<String> input = new ArrayList<>();
      input.add("p1");
      input.add("1000");
      input.add("2022-10-21");
      input.add("2.3");
      input.add("2");
      return input;
    }

    @Override
    public Map<String, Double> getInvestmentShares(int num) throws IllegalArgumentException {
      Map<String, Double> m = new HashMap<>();
      m.put("aapl", 70.0);
      m.put("amzn", 30.0);
      return m;
    }

    @Override
    public ArrayList<String> getDollarCostDetails() throws IllegalArgumentException {
      ArrayList<String> input = new ArrayList<>();
      input.add("p1");
      input.add("1000");
      input.add("2.3");
      input.add("2022-10-21");
      input.add("");
      input.add("30");
      input.add("2");
      return input;
    }

    @Override
    public void showBlank() {
      out.print("Input fields cannot be blank");
    }

    @Override
    public void showChart(Map<String, Double> m, String pName, String sDate, String eDate) {

    }
  }
}
