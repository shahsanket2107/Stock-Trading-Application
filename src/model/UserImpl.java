package model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * This class has all the functions of user model. As the user has a portfolio, this class has a
 * portfolio object which calls its methods. This class is called by the controller. In the second
 * part of the assignment this class also stores a list of flexible portfolio and the data store api
 * to read from cache instead of triggering api calls multiple times. Also it has implementation of
 * all methods for flexible portfolios.
 */

public class UserImpl implements User {

  private List<Portfolio> portfolio;
  private String name;
  private List<FlexiblePortfolio> flexiblePortfolio;
  private DataStoreFromApi data_store;

  /**
   * Default constructor for User Impl used to initialize the instance variables.
   */
  public UserImpl() {
    this.name = "John Doe";
    this.portfolio = new ArrayList<>();
    this.flexiblePortfolio = new ArrayList<>();
    this.data_store = new DataStoreFromApiImpl();
  }

  /**
   * Parameterised constructor for User Impl used to initialize the instance variables.
   *
   * @param name              is the name of the portfolio.
   * @param portfolio         is the inflexible list of portfolios.
   * @param flexiblePortfolio is the flexible list of portfolios.
   */
  public UserImpl(String name, List<Portfolio> portfolio,
                  List<FlexiblePortfolio> flexiblePortfolio) {
    this.name = name;
    this.portfolio = portfolio;
    this.flexiblePortfolio = flexiblePortfolio;
    this.data_store = new DataStoreFromApiImpl();
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
    if (checkPortfolioExists(portfolioName) == 2 || checkPortfolioExists(portfolioName) == 1) {
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
  public StringBuilder getTotalValuation(String date, String pName) throws IllegalArgumentException {
    dateFormatHelper(date);
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
  public void createFlexiblePortfolio(String portfolioName, List<Stocks> stocks, double commissionFee)
          throws IllegalArgumentException {
    if (checkPortfolioExists(portfolioName) == 2 || checkPortfolioExists(portfolioName) == 1) {
      throw new IllegalArgumentException("Portfolio with the given name already exists!!");
    }

    dataStoreHelper(stocks);
    for (Stocks s : stocks) {
      costBasisHelper(s, commissionFee);
    }
    String fileName = this.name + "_portfolios.json";
    FileOperations write = new FileOperationsImpl();
    write.writeToJson(fileName, portfolioName, stocks);
    flexiblePortfolio.add(new FlexiblePortfolioImpl(portfolioName, stocks));
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
  public boolean validateDateAccToApi(String ticker, String date) {
    Set set = data_store.getTickers();
    if (!set.contains(ticker)) {
      data_store.fetchFromApi(ticker);
    }
    Map<String, JsonNode> m = data_store.getApiData();
    JsonNode tempNode = m.get(ticker);
    JsonNode temp = tempNode.get(date);
    return temp != null;
  }

  @Override
  public StringBuilder getCostBasis(String date, String pName) throws IllegalArgumentException {
    dateFormatHelper(date);
    StringBuilder temp = new StringBuilder();
    FlexiblePortfolio p;
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        temp.append("Portfolio_Name: ").append(p.getName());
        temp.append("\n");
        List<Stocks> stocks = p.getStocks();
        Double tempResult = 0.0;
        for (Stocks s : stocks) {
          if (Integer.parseInt(formatDate(date)) >= Integer.parseInt(formatDate(s.getDate()))) {
            tempResult += s.getCostBasis();
          }

        }
        temp.append("Cost basis of your portfolio at ").append(date).append(" is : $ ")
                .append(tempResult);
        temp.append("\n");
      }
    }
    if (temp.toString().equals("")) {
      temp.append("Given portfolio doesn't exist!!");
    }
    return temp;
  }


  protected Map<String, Double> getPortfolioCompositionOnADateHelper(String pName, String date)
          throws IllegalArgumentException {
    Map<String, Double> m = new HashMap<>();
    double tempQty;
    int flg = 0;
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      if (pName.equals(value.getName())) {
        flg = 1;
        List<Stocks> stocks = value.getStocks();
        List<String> non_unique_stocks = new ArrayList<>();

        for (Stocks stock : stocks) {
          non_unique_stocks.add(stock.getTicker());
        }
        Set<String> unique_stocks = new HashSet<>(non_unique_stocks);

        for (String ticker : unique_stocks) {
          tempQty = 0;
          for (Stocks s : stocks) {
            if (s.getTicker().equals(ticker)) {
              if (dateCompare(date, s.getDate())) {
                tempQty += s.getQty();
              }
            }
          }
          m.put(ticker, tempQty);
        }
      }
    }
    if (flg == 0) {
      throw new IllegalArgumentException();
    }
    return m;
  }

  @Override
  public StringBuilder getFlexiblePortfolioComposition(String pName, String date)
          throws IllegalArgumentException {
    StringBuilder temp = new StringBuilder();
    dateFormatHelper(date);

    Map<String, Double> m;
    Map<String, Double> result = new HashMap<>();
    try {
      m = getPortfolioCompositionOnADateHelper(pName, date);
    } catch (IllegalArgumentException e) {
      temp.append(
              "The given portfolio name does not exist!!\nPlease enter a valid portfolio name!!");
      return temp;
    }
    for (String s : m.keySet()) {
      double value = m.get(s);
      if (value > 0) {
        result.put(s, value);
      }
    }
    try {
      ObjectMapper mapper = new ObjectMapper();
      String temp2 = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(result);
      temp.append("The portfolio composition of portfolio " + pName + " on " + date + " is:-\n");
      temp.append(temp2);
    } catch (Exception e) {
      temp.append("Error in reading the portfolio!!");
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
  public String buyStocks(String ticker, double qty, String pName, String date,
                          double commissionFee) throws IllegalArgumentException {

    dateFormatHelper(date);

    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      if (pName.equals(value.getName())) {
        Stocks stocks = new StocksImpl(date, ticker, qty);
        value.getStocks().add(stocks);
        dataStoreHelper(value.getStocks());
        costBasisHelper(stocks, commissionFee);
        String fileName = this.name + "_portfolios.json";
        FileOperations write = new FileOperationsImpl();
        write.editJson(fileName, pName, value.getStocks());
        break;
      }
    }
    return "Stocks bought successfully and added to your portfolio!";

  }

  @Override
  public String sellStocks(String ticker, double qty, String pName, String date,
                           double commissionFee) throws IllegalArgumentException {

    int tempQty = 0;
    int flg = 0;

    dateFormatHelper(date);
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      if (pName.equals(value.getName())) {
        List<Stocks> stocks = value.getStocks();
        for (Stocks stock : stocks) {
          if (stock.getTicker().equals(ticker)) {
            flg = 1;
            String sDate = stock.getDate();
            if (dateCompare(date, sDate)) {
              tempQty += stock.getQty();
            }
          }
        }
        if (flg == 0) {
          return "The entered stock does not exist in your portfolio!";
        }
        if (tempQty >= qty) {
          Stocks s = new StocksImpl(date, ticker, qty * -1);
          s.setCostBasis(commissionFee);
          value.getStocks().add(s);
          String fileName = this.name + "_portfolios.json";
          FileOperations write = new FileOperationsImpl();
          write.editJson(fileName, pName, value.getStocks());
        } else {
          return "Quantity entered is more than what you have in your portfolio!";
        }
      }
    }
    return "Stocks sold successfully!";
  }

  @Override
  public StringBuilder getFlexiblePortfolioTotalValuation(String date, String pName) throws
          IllegalArgumentException {
    dateFormatHelper(date);
    StringBuilder temp = new StringBuilder();
    FlexiblePortfolio p;
    for (FlexiblePortfolio value : this.flexiblePortfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        temp.append("Portfolio_Name: ").append(p.getName());
        temp.append("\n");
        List<Stocks> stocks = p.getStocks();
        if (!stocks.isEmpty() && !validateDateAccToApi(stocks.get(0).getTicker(), date)) {
          StringBuilder temp2 = new StringBuilder();
          temp2.append("Stock market is closed at the date: " + date + ". So please enter a " +
                  "different date\n");
          return temp2;
        }
        Map<String, JsonNode> m = data_store.getApiData();
        Map<String, Double> m1;
        try {
          m1 = getPortfolioCompositionOnADateHelper(pName, date);
        } catch (IllegalArgumentException e) {
          temp.append("Given portfolio doesn't exist!!");
          return temp;
        }
        temp = getFlexibleTotalValuationHelper(temp, m, m1, date);
      }
    }
    return temp;
  }

  private StringBuilder getFlexibleTotalValuationHelper(StringBuilder temp, Map<String, JsonNode> m,
                                                        Map<String, Double> m1, String date) {
    Double ans = 0.0;
    int flg = 0;
    temp.append("The stock valuation breakdown is: \n");
    for (String s : m1.keySet()) {
      String ticker = s;
      double qty = m1.get(ticker);
      if (qty <= 0) {
        continue;
      }
      JsonNode tempNode = m.get(ticker);
      temp.append(ticker);
      temp.append(" : $ ");
      String tempResult;
      tempResult = String.valueOf(tempNode.get(date).get("4. close"));
      tempResult = tempResult.replaceAll("\"", "");
      double tempCompute = Double.parseDouble(tempResult) * qty;
      temp.append(tempCompute);
      temp.append("\n");
      flg = 1;
      ans = ans + tempCompute;
    }
    if (flg == 0) {
      temp = new StringBuilder();
    }
    temp.append("Portfolio_Valuation at ").append(date).append(" is : $ ")
            .append(ans);
    temp.append("\n");
    return temp;
  }

  protected void dateFormatHelper(String date) throws IllegalArgumentException {
    String temp_date = date.replaceAll("[\\s\\-()]", "");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
    if (Integer.parseInt(temp_date) >= Integer.parseInt(curr_date)) {
      throw new IllegalArgumentException(
              "Date cannot be greater or equal to current date. Try a different date");
    }
    if (Integer.parseInt(temp_date) <= 20000101) {
      throw new IllegalArgumentException(
              "Date should be more than 1st January 2000. Try a different date");
    }
  }

  protected boolean dateCompare(String date1, String date2) {
    String temp_date1 = date1.replaceAll("[\\s\\-()]", "");
    String temp_date2 = date2.replaceAll("[\\s\\-()]", "");

    return Integer.parseInt(temp_date1) >= Integer.parseInt(temp_date2);
  }

  private String formatDate(String date) {
    return date.replaceAll("[\\s\\-()]", "");
  }

  protected Double getStockValuationAtADate(String ticker, String date) {
    Map<String, JsonNode> m = data_store.getApiData();
    JsonNode tempNode = m.get(ticker);
    String tempResult;
    try {
      tempResult = String.valueOf(tempNode.get(date).get("4. close"));
    } catch (NullPointerException e) {
      return -1.0;
    }
    tempResult = tempResult.replaceAll("\"", "");
    double tempCompute = Double.parseDouble(tempResult);
    return tempCompute;
  }

  private void costBasisHelper(Stocks stock, double commissionFee) {
    Double temp = getStockValuationAtADate(stock.getTicker(), stock.getDate());
    double qty = stock.getQty();
    stock.setCostBasis(temp * qty + commissionFee);
  }

  @Override
  public StringBuilder displayChart(String startDate, String endDate, String pName)
          throws IllegalArgumentException {
    if (dateCompare(startDate, endDate)) {
      return new StringBuilder("Start date cannot be more than end date!");
    }
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date e;
    Date s;
    try {
      s = sdf.parse(startDate);
      e = sdf.parse(endDate);
    } catch (ParseException ex) {
      throw new IllegalArgumentException("Error in parsing date!");
    }
    long diff = TimeUnit.DAYS.convert(Math.abs(e.getTime() - s.getTime()),
            TimeUnit.MILLISECONDS);

    long timeLine = diff;
    int week = 0;
    int month = 0;
    int year = 0;
    Calendar c = Calendar.getInstance();
    c.setTime(s);
    if (diff > 30) {
      timeLine = diff / 7;
      week = 1;
      if (timeLine > 20) {
        timeLine = timeLine / 4;
        month = 1;
        if (timeLine > 30) {
          timeLine = timeLine / 12;
          year = 1;
        }
      }
    }
    ArrayList<String> dates = getDatesForChart(year, month, week, timeLine, c, e);

    Map<String, Double> m = insertValueInMapForChart(pName, dates, c);

    StringBuilder result = printChart(m, year, month, c, startDate, endDate, pName);
    return result;
  }

  @Override
  public List<FlexiblePortfolio> getFlexiblePortfolioList() {
    return this.flexiblePortfolio;
  }

  @Override
  public DataStoreFromApi getDataStore() {
    return this.data_store;
  }

  private ArrayList<String> getDatesForChart(int year, int month, int week, long timeLine,
                                             Calendar c, Date e) {
    ArrayList<String> dates = new ArrayList<>();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    if (year == 1) {
      for (int i = 0; i < timeLine; i++) {
        c.set(Calendar.DAY_OF_YEAR, c.getActualMaximum(Calendar.DAY_OF_YEAR));
        if ((c.getTime()).compareTo(e) > 0) {
          dates.add(sdf.format(e));
        } else {
          dates.add(sdf.format(c.getTime()));
          c.add(Calendar.YEAR, 1);
        }
      }
    } else if (month == 1) {
      for (int i = 0; i < timeLine; i++) {
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        if ((c.getTime()).compareTo(e) > 0) {
          dates.add(sdf.format(e));
        } else {
          dates.add(sdf.format(c.getTime()));
          c.add(Calendar.MONTH, 1);
        }
      }
    } else if (week == 1) {
      for (int i = 0; i < timeLine; i++) {
        dates.add(sdf.format(c.getTime()));
        c.add(Calendar.WEEK_OF_YEAR, 1);
      }
    } else {
      for (int i = 0; i <= timeLine; i++) {
        dates.add(sdf.format(c.getTime()));
        c.add(Calendar.DATE, 1);
      }
    }
    return dates;
  }

  private StringBuilder printChart(Map<String, Double> m, int year, int month, Calendar c,
                                   String startDate, String endDate, String pName)
          throws IllegalArgumentException {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Double maxValueInMap = (Collections.max(m.values()));
    Double minValueInMap = (Collections.min(m.values()));

    double print;
    int scale = (int) ((maxValueInMap - minValueInMap) / 10);
    StringBuilder star = new StringBuilder();
    star.append("Performance of portfolio " + pName + " from " + startDate + " to " + endDate);
    star.append("\n");
    for (Entry<String, Double> entry : m.entrySet()) {
      print = entry.getValue() / scale;
      int p = (int) Math.round(print);
      try {
        if (year == 1) {
          c.setTime(sdf.parse(entry.getKey()));
          star.append(c.get(Calendar.YEAR));
        } else if (month == 1) {
          Format f = new SimpleDateFormat("MMM");
          Format yf = new SimpleDateFormat("yyyy");
          star.append(f.format(sdf.parse(entry.getKey()))).append(" ")
                  .append(yf.format(sdf.parse(entry.getKey())));
        } else {
          star.append(entry.getKey());
        }
      } catch (ParseException ex) {
        throw new IllegalArgumentException("Error in parsing date!");
      }
      star.append(": ");

      for (int i = 1; i <= p; i++) {
        star.append("*");
      }
      star.append("\n");
    }
    star.append("Scale: * = $").append(scale);
    return star;
  }

  private Map<String, Double> insertValueInMapForChart(String pName, ArrayList<String> dates,
                                                       Calendar c) throws IllegalArgumentException {
    String temp_date;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Map<String, Double> m = new TreeMap<>();
    for (int i = 0; i < dates.size(); i++) {
      temp_date = (dates.get(i));
      String value;
      Double d;
      value = String.valueOf(getFlexiblePortfolioTotalValuation(temp_date, pName));
      if (value.charAt(0) == 'S') {
        while (value.charAt(0) == 'S') {
          try {
            value = String.valueOf(getFlexiblePortfolioTotalValuation(temp_date, pName));
            c.setTime(sdf.parse(temp_date));
            c.add(Calendar.DATE, 1);
            temp_date = sdf.format(c.getTime());
          } catch (Exception ex) {
            throw new IllegalArgumentException("Error in parsing!!");
          }
        }
        d = Double.parseDouble(value.substring(value.lastIndexOf(" ") + 1));
      } else {
        try {
          d = Double.parseDouble(value.substring(value.lastIndexOf(" ") + 1));
        } catch (Exception exception) {
          d = 0.0;
        }
      }
      m.put(dates.get(i), d);
    }
    return m;
  }

  protected List<String> getPortfolioNames() {
    List<String> ans = new ArrayList<>();
    for (FlexiblePortfolio p : flexiblePortfolio) {
      ans.add(p.getName());
    }
    return ans;
  }
}

