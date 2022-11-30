package model;


import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * This class implements the UserModelExtension interface and extends the UserImpl class. This class
 * is used to implement the methods of the new strategy development according to dollar cost
 * averaging and also investment feature. It also overrides the UserImpl methods that require to
 * be checked for changing composition of future transactions.
 */
public class UserModelExtensionImpl extends UserImpl implements UserModelExtension {

  /**
   * Default constructor for ExtensionImpl class which calls the parent default constructor.
   */
  public UserModelExtensionImpl() {
    super();
  }

  private double getValuationHelper(String ticker, String date) throws IllegalArgumentException {
    double temp = -1;
    Calendar c = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    while (temp == -1) {
      temp = super.getStockValuationAtADate(ticker, date);
      if (temp != -1) {
        break;
      }
      try {
        c.setTime(sdf.parse(date));
        c.add(Calendar.DATE, 1);
        date = sdf.format(c.getTime());
      } catch (ParseException e) {
        throw new IllegalArgumentException("Error in parsing date!!");
      }
    }
    return temp;
  }

  private void invalidInputHelperForFractionalPercentage(String date,
                                                         double amount, Map<String, Double> m,
                                                         double commissionFee)
          throws IllegalArgumentException {
    if (!super.isValidFormat(date)) {
      throw new IllegalArgumentException("Date is not in proper format !!");
    }
    super.dateFormatHelper(date);
    if (amount <= 0) {
      throw new IllegalArgumentException("Amount should be positive !!");
    }
    if (commissionFee < 0) {
      throw new IllegalArgumentException("Commission Fee must be positive !!");
    }
    double temp = 0;
    for (String ticker : m.keySet()) {
      if (m.get(ticker) < 0) {
        throw new IllegalArgumentException("The fractional share cannot be negative!!");
      }
      temp += m.get(ticker);
    }
    if (temp != 100.0) {
      throw new IllegalArgumentException("The fractional shares must sum up to be 100% !!");
    }
  }

  @Override
  public boolean investFractionalPercentage(String pname, String date,
                                            double amount, Map<String, Double> m,
                                            double commissionFee) throws IllegalArgumentException {
    List<String> validPortfolios = super.getPortfolioNames();
    if (!validPortfolios.contains(pname)) {
      throw new IllegalArgumentException("Portfolio Name does not exist !!");
    }
    try {
      invalidInputHelperForFractionalPercentage(date, amount, m, commissionFee);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    List<FlexiblePortfolio> flexiblePortfolioList = super.getFlexiblePortfolioList();
    double finalAmount = amount - commissionFee;
    int flg = 0;
    for (FlexiblePortfolio p : flexiblePortfolioList) {
      if (p.getName().equals(pname)) {
        flg = 1;
        double commissionFeePerStock = commissionFee / m.size();
        m.forEach((k, v) -> {
          dataStoreHelper(k);
          double temp = getValuationHelper(k, date);
          double fractionalShare = finalAmount * v / 100.0;
          double qty = fractionalShare / temp;
          Stocks tempStock = new StocksImpl(date, k, qty);
          tempStock.setCostBasis(qty * temp + commissionFeePerStock);
          p.getStocks().add(tempStock);
        });
        String fileName = super.getName() + "_portfolios.json";
        FileOperations write = new FileOperationsImpl();
        write.editJson(fileName, pname, p.getStocks());
        break;
      }
    }
    if (flg == 0) {
      throw new IllegalArgumentException("Portfolio Name does not exist!!");
    }
    return true;
  }

  private void dataStoreHelper(String ticker) throws IllegalArgumentException {
    Set set = super.getDataStore().getTickers();
    if (!set.contains(ticker)) {
      super.getDataStore().fetchFromApi(ticker);
    }
  }

  private void dollarCostAveragingHelper(String pname, Map<String, Double> m,
                                         double amount, double commissionFee,
                                         String startDate, String endDate, int interval) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    Date currentDate = new Date();
    String fEndDate = endDate;
    String formattedCurrentDate = sdf.format(currentDate);
    if (endDate.isEmpty()) {
      fEndDate = formattedCurrentDate;
    }
    Date s;
    while (super.dateCompare(fEndDate, startDate)) {
      try {
        s = sdf.parse(startDate);
      } catch (Exception e) {
        throw new IllegalArgumentException("Error in parsing date!!");
      }
      if (super.dateCompare(startDate, formattedCurrentDate)) {
        futureDatesStrategyHelper(pname, startDate, endDate, amount, m, commissionFee, interval);
        break;
      }
      investFractionalPercentage(pname, startDate, amount, m, commissionFee);
      c.setTime(s);
      c.add(Calendar.DATE, interval);
      startDate = sdf.format(c.getTime());
    }
    if (!fEndDate.equals(endDate)) {
      futureDatesStrategyHelper(pname, startDate, endDate, amount, m, commissionFee, interval);
    }
  }

  private String compareStartDateHelper(String startDate, String endDate, int interval) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    Date currentDate = new Date();
    String formattedCurrentDate = sdf.format(currentDate);
    Date s;
    String fEndDate = endDate;
    String fStartDate = startDate;
    if (endDate.isEmpty()) {
      fEndDate = formattedCurrentDate;
    }
    while (super.dateCompare(fEndDate, fStartDate)) {
      try {
        s = sdf.parse(fStartDate);
      } catch (Exception e) {
        throw new IllegalArgumentException("Error in parsing date!!");
      }
      if (super.dateCompare(fStartDate, formattedCurrentDate)) {
        return fStartDate;
      }
      c.setTime(s);
      c.add(Calendar.DATE, interval);
      fStartDate = sdf.format(c.getTime());
    }
    return startDate;
  }

  @Override
  public boolean dollarCostAveragingPortfolio(String pname, Map<String, Double> m,
                                              double amount, double commissionFee,
                                              String startDate, String endDate, int interval)
          throws IllegalArgumentException {
    try {
      invalidInputHelperForFractionalPercentage(startDate, amount, m, commissionFee);
    } catch (IllegalArgumentException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
    if (interval <= 0) {
      throw new IllegalArgumentException("Time interval to invest must be a positive number of " +
              "days!!");
    }
    if (!endDate.isEmpty() && super.dateCompare(startDate, endDate)) {
      throw new IllegalArgumentException("Start date cannot be more than end date!");
    }
    int chk = super.checkPortfolioExists(pname);
    if (chk == 0) {
      super.createFlexiblePortfolio(pname, new ArrayList<>(), commissionFee);
    }
    dollarCostAveragingHelper(pname, m, amount, commissionFee, startDate, endDate, interval);
    return true;
  }

  private void futureDatesStrategyHelper(String pname, String startDate, String endDate,
                                         double amount, Map<String, Double> m,
                                         double commissionFee, int interval)
          throws IllegalArgumentException {

    String fileName = "user_persistance.json";
    FileOperations file = new FileOperationsImpl();
    file.futureDatesStrategyHelper(fileName, pname, m, amount, commissionFee, startDate,
            endDate, interval);
  }

  @Override
  public boolean loadPersistantStrategy(String pname) throws IllegalArgumentException {
    String fileName = "user_persistance.json";
    JSONObject result;
    int flg = 0;
    try {
      FileReader reader = new FileReader(fileName);
      JSONParser parser = new JSONParser();
      result = (JSONObject) parser.parse(reader);
      JSONArray values = (JSONArray) result.get(pname);
      if (values == null) {
        return false;
      }
      for (int i = 0; i < values.size(); i++) {
        JSONObject temp = (JSONObject) values.get(i);
        double amount = Double.parseDouble(temp.get("amount").toString());
        double commissionFee = Double.parseDouble(temp.get("commissionFee").toString());
        int interval = Integer.parseInt(temp.get("interval").toString());
        String startDate = temp.get("startDate").toString();
        String endDate = temp.get("endDate").toString();
        Map<String, Double> map = (Map<String, Double>) temp.get("fractionalShares");
        String newStartDate = compareStartDateHelper(startDate, endDate, interval);
        temp.put("startDate", newStartDate);
        if (!newStartDate.equals(startDate)) {
          flg = 1;
          dollarCostAveragingPortfolio(pname, map, amount, commissionFee, startDate, newStartDate,
                  interval);
        }
      }

    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error in parsing the file!!");
    }
    if (flg == 1) {
      try {
        FileWriter writer = new FileWriter(fileName);
        writer.write(result.toJSONString());
        writer.close();
        return true;
      } catch (Exception e) {
        throw new IllegalArgumentException("Error in writing to file!!");
      }
    }
    return false;
  }

  @Override
  public Map<String, Double> showPerformance(String pName, String startDate, String endDate)
          throws IllegalArgumentException {
    loadPersistantStrategy(pName);
    if (dateCompare(startDate, endDate)) {
      throw new IllegalArgumentException("Start date cannot be more than end date!");
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
    return insertValueInMapForChart(pName, dates, c);
  }

  @Override
  public StringBuilder getFlexiblePortfolioComposition(String pName, String date)
          throws IllegalArgumentException {
    loadPersistantStrategy(pName);
    return super.getFlexiblePortfolioComposition(pName, date);
  }

  @Override
  public StringBuilder getCostBasis(String date, String pName)
          throws IllegalArgumentException {
    loadPersistantStrategy(pName);
    return super.getCostBasis(date, pName);
  }

  @Override
  public StringBuilder getFlexiblePortfolioTotalValuation(String date, String pName)
          throws IllegalArgumentException {
    loadPersistantStrategy(pName);
    return super.getFlexiblePortfolioTotalValuation(date, pName);
  }

}
