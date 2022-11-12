package model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class has all the functions of user model. As the user has a portfolio, this class has a
 * portfolio object which calls its methods. This class is called by the controller.
 */

public class UserImpl implements User {

  private List<Portfolio> portfolio;
  private String name;
  private List<FlexiblePortfolio> flexiblePortfolio;
  private DataStoreFromApi data_store;
  private int commissionFee = 0;

  public UserImpl() {
    this.name = "John Doe";
    this.portfolio = new ArrayList<>();
    this.flexiblePortfolio = new ArrayList<>();
    this.data_store = new DataStoreFromApiImpl();
  }

  public UserImpl(String name, List<Portfolio> portfolio,
      List<FlexiblePortfolio> flexiblePortfolio) {
    this.name = name;
    this.portfolio = portfolio;
    this.flexiblePortfolio = flexiblePortfolio;
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
    FlexiblePortfolio fp;
    tp.append("The list of portfolios is:\n");
    for (Portfolio value : this.portfolio) {
      p = value;
      temp.append(p.getName() + "\n");
    }
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      fp = value;
      temp.append(fp.getName() + "\n");
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
    if (checkPortfolioExists(portfolioName) == 1 || checkPortfolioExists(portfolioName) == 2) {
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
    StringBuilder check = dateFormatHelper(date);
    if (!check.isEmpty()) {
      return check;
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
  public int checkPortfolioExists(String pName) {
    int flg = 0;
    for (Portfolio value : this.portfolio) {
      if (value.getName().equals(pName)) {
        flg = 1;
        break;
      }
    }
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      if (value.getName().equals(pName)) {
        flg = 2;
        break;
      }
    }
    return flg;
  }

  @Override
  public void createFlexiblePortfolio(String portfolioName, List<Stocks> stocks)
      throws IllegalArgumentException {
    if (checkPortfolioExists(portfolioName) == 2 || checkPortfolioExists(portfolioName) == 1) {
      throw new IllegalArgumentException("Portfolio with the given name already exists!!");
    }
    String fileName = this.name + "_portfolios.json";
    FileOperations write = new FileOperationsImpl();
    write.writeToJson(fileName, portfolioName, stocks);
    flexiblePortfolio.add(new FlexiblePortfolioImpl(portfolioName, stocks));
    dataStoreHelper(stocks);
  }

  private void dataStoreHelper(List<Stocks> stocks) throws IllegalArgumentException {
    Set set = data_store.getTickers();
    for (Stocks s : stocks) {
      if (!set.contains(s.getTicker())) {
        data_store.fetchFromApi(s.getTicker());
      }
    }
  }

  @Override
  public StringBuilder getFlexiblePortfolioComposition(String pName) {
    StringBuilder temp = new StringBuilder();
    FlexiblePortfolio p;
    int flg = 0;
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        flg = 1;
        try {
          ObjectMapper mapper = new ObjectMapper();
          String temp2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(p.getStocks());
          temp.append(temp2);
        } catch (Exception e) {
          temp.append("Error in reading the portfolio!!");
        }
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
  public String loadFlexiblePortfolio(String fileName) {
    try {
      FileOperations read = new FileOperationsImpl();
      this.flexiblePortfolio = read.readFromJson(fileName);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error in loading flexible portfolio!!");
    }
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      dataStoreHelper(value.getStocks());
    }
    return "Flexible Portfolio loaded successfully!";
  }

  @Override
  public String buyStocks(String ticker, int qty, String pName, String date) {

    String temp_date = date.replaceAll("[\\s\\-()]", "");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
    if (Integer.parseInt(temp_date) >= Integer.parseInt(curr_date)) {
      return "Date cannot be greater or equal to current date.";
    }
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      if (pName.equals(value.getName())) {
        Stocks stocks = new StocksImpl(date, ticker, qty);
        value.getStocks().add(stocks);
        String fileName = this.name + "_portfolios.json";
        FileOperations write = new FileOperationsImpl();
        write.editJson(fileName, pName, value.getStocks());
        dataStoreHelper(value.getStocks());
        break;
      }
    }
    return "Stocks bought successfully and added to your portfolio!";

  }

  @Override
  public String sellStocks(String ticker, int qty, String pName, String date) {
    String message = "";
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      if (pName.equals(value.getName())) {
        List<Stocks> stocks = value.getStocks();
        for (Stocks stock : stocks) {
          if (stock.getTicker().equals(ticker)) {
            if (stock.getQty() >= qty) {
              String pDate = stock.getDate();
              String temp_date = date.replaceAll("[\\s\\-()]", "");
              pDate = pDate.replaceAll("[\\s\\-()]", "");
              DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
              LocalDateTime now = LocalDateTime.now();
              String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
              if (Integer.parseInt(temp_date) >= Integer.parseInt(curr_date)) {
                return "Date cannot be greater or equal to current date.";
              }
              if (Integer.parseInt(temp_date) <= Integer.parseInt(pDate)) {
                return "Date should be more than the date when you bought the stocks.";
              }
              stock.setQty(stock.getQty() - qty);
              message = "Stocks sold successfully!";
              String fileName = this.name + "_portfolios.json";
              FileOperations write = new FileOperationsImpl();
              write.editJson(fileName, pName, stocks);
            } else {
              return "Quantity entered is more than what you have in your portfolio!";
            }
          } else {
            message = "The entered stock does not exist in your portfolio!";
          }
        }
      }

    }
    return message;
  }

  @Override
  public void display() {
    data_store.display();
  }

  @Override
  public StringBuilder getFlexiblePortfolioTotalValuation(String date, String pName) {
    StringBuilder check = dateFormatHelper(date);
    if (!check.isEmpty()) {
      return check;
    }
    StringBuilder temp = new StringBuilder();
    FlexiblePortfolio p;
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        temp.append("Portfolio_Name: ").append(p.getName());
        temp.append("\n");
        List<Stocks> stocks = p.getStocks();
        Map<String, JsonNode> m = data_store.getApi_data();
        Double ans = 0.0;

        temp.append("The stock valuation breakdown is: \n");
        for (Stocks s : stocks) {
          JsonNode tempNode = m.get(s.getTicker());
          temp.append(s.getTicker());
          temp.append(" : $ ");
          String tempResult = String.valueOf(tempNode.get(date).get("4. close"));
          tempResult = tempResult.replaceAll("\"", "");
          temp.append(tempResult);
          temp.append("\n");
          ans = ans + Double.parseDouble(tempResult);
        }
        temp.append("Portfolio_Valuation at ").append(date).append(" is : $ ")
            .append(ans);
        temp.append("\n");
      }
    }
    if (temp.toString().equals("")) {
      temp.append("Given portfolio doesn't exist!!");
    }
    return temp;
  }


  private StringBuilder dateFormatHelper(String date) {
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
    return new StringBuilder();
  }
}
