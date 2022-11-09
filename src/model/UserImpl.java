package model;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class has all the functions of user model. As the user has a portfolio, this class has a
 * portfolio object which calls its methods. This class is called by the controller.
 */

public class UserImpl implements User {

  private List<Portfolio> portfolio;
  private String name;

  public UserImpl() {
    this.name = "John Doe";
    this.portfolio = new ArrayList<>();
  }

  public UserImpl(String name, List<Portfolio> portfolio) {
    this.name = name;
    this.portfolio = portfolio;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public StringBuilder getPortfoliosName() {
    StringBuilder temp = new StringBuilder();
    StringBuilder tp = new StringBuilder();
    Portfolio p;
    tp.append("The list of portfolios is:\n");
    for (Portfolio value : this.portfolio) {
      p = value;
      temp.append(p.getName() + "\n");
    }
    if (temp.toString().equals("")) {
      temp.append("No portfolios exist at this time!! ");
      return temp;
    }
    tp.append(temp);
    return tp;
  }

  @Override
  public void createPortfolio(String portfolioName, Map<String, Integer> stocks)
      throws IllegalArgumentException {
    if (checkPortfolioExists(portfolioName)) {
      throw new IllegalArgumentException("Portfolio with the given name already exists!!");
    }
    String fileName = this.name + "_portfolios.xml";
    FileOperations write = new FileOperationsImpl();
    write.writeToFile(fileName, portfolioName, stocks);
    portfolio.add(new PortfolioImpl(portfolioName, stocks));
  }


  @Override
  public String loadPortfolio(String pfName) throws IllegalArgumentException {
    FileOperations read = new FileOperationsImpl();
    this.portfolio = read.readFromFile(pfName);
    return "Portfolio loaded successfully!";
  }

  @Override
  public boolean ifStocksExist(String ticker) {
    boolean flag = false;
    for (Ticker i : Ticker.values()) {
      if (i.name().equalsIgnoreCase(String.valueOf(ticker))) {
        flag = true;
        break;
      }
    }
    return flag;
  }

  @Override
  public boolean isValidFormat(String value) {
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      date = sdf.parse(value);
      if (!value.equals(sdf.format(date))) {
        date = null;
      }
    } catch (ParseException ex) {
      return false;
    }
    return date != null;
  }

  @Override
  public StringBuilder getTotalValuation(String date, String pName) {
    String temp_date = date.replaceAll("[\\s\\-()]", "");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
    if (Integer.parseInt(temp_date) >= Integer.parseInt(curr_date)) {
      return new StringBuilder(
          "Date cannot be greater or equal to current date. Try a different date");
    }
    if (Integer.parseInt(temp_date) <= 20000101) {
      return new StringBuilder("Date should be more than 1st January 2000. Try a different date");
    }
    StringBuilder temp = new StringBuilder();
    Portfolio p;
    for (Portfolio value : this.portfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        temp.append("Portfolio_Name: ").append(p.getName());
        temp.append("\n");
        Map<String, Double> m = p.getValuationAtDate(date);
        Double ans = computeValue(m);
        temp.append("Portfolio_Valuation at ").append(date).append(" is : $ ")
            .append(ans);
        temp.append("\n");
        temp.append("The stock valuation breakdown is: \n");
        m.forEach((k, v) -> {
          temp.append(k + " : $" + v + "\n");
        });
      }
    }
    if (temp.toString().equals("")) {
      temp.append("Given portfolio doesn't exist!!");
    }
    return temp;
  }

  private double computeValue(Map<String, Double> m) {
    AtomicReference<Double> ans = new AtomicReference<>((double) 0);
    m.forEach((k, v) -> ans.updateAndGet(v1 -> v1 + v));
    return ans.get();
  }

  @Override
  public StringBuilder getPortfolioComposition(String pName) {
    StringBuilder temp = new StringBuilder();
    Portfolio p;
    int flg = 0;
    for (Portfolio value : this.portfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        flg = 1;
        temp.append("Portfolio_Name: ").append(p.getName());
        temp.append("\n");
        Map<String, Integer> m = p.getStocks();
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
          temp.append("{\n");
          temp.append("\tStock_Ticker: ").append(entry.getKey()).append("\n");
          temp.append("\tQuantity: ").append(entry.getValue()).append("\n");
          temp.append("}\n");
        }
        temp.append("\n\n");
        break;
      }
    }
    if (flg == 0) {
      temp.append(
          "The given portfolio name does not exist!!\nPlease enter a valid portfolio name!!");
    }
    return temp;
  }

  @Override
  public boolean checkPortfolioExists(String pName) {
    int flg = 0;
    for (Portfolio value : this.portfolio) {
      if (value.getName().equals(pName)) {
        flg = 1;
        break;
      }
    }
    return flg == 1;
  }
}
