package model;


import org.codehaus.jackson.JsonNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class UserModelExtensionImpl extends UserImpl implements UserModelExtension {


  public UserModelExtensionImpl() {
    super();
  }

  public UserModelExtensionImpl(String name, List<Portfolio> portfolio,
                                List<FlexiblePortfolio> flexiblePortfolio) {
    super(name, portfolio, flexiblePortfolio);
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

  @Override
  public String investFractionalPercentage(String pname, String date,
                                           double amount, Map<String, Double> m,
                                           double commissionFee) throws IllegalArgumentException {
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
      return "Portfolio Name does not exist!!";
    }
    return "Amount Invested Successfully!!";
  }

  @Override
  public List<String> setOfStocks(String pName, String date) throws IllegalArgumentException {
    List<String> stocksSet = new ArrayList();
    Map<String, Double> m = super.getPortfolioCompositionOnADateHelper(pName, date);

    for (String s : m.keySet()) {
      double value = m.get(s);
      if (value > 0) {
        stocksSet.add(s);
      }
    }
    return stocksSet;
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
    String formattedCurrentDate = sdf.format(currentDate);
    Date s;
    while (super.dateCompare(endDate, startDate)) {
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
  }

  private String compareStartDateHelper(String startDate, String endDate, int interval) {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    Date currentDate = new Date();
    String formattedCurrentDate = sdf.format(currentDate);
    Date s;
    while (super.dateCompare(endDate, startDate)) {
      try {
        s = sdf.parse(startDate);
      } catch (Exception e) {
        throw new IllegalArgumentException("Error in parsing date!!");
      }
      if (super.dateCompare(startDate, formattedCurrentDate)) {
        return startDate;
      }
      c.setTime(s);
      c.add(Calendar.DATE, interval);
      startDate = sdf.format(c.getTime());
    }
    return endDate;
  }

  @Override
  public String dollarCostAveragingPortfolio(String pname, Map<String, Double> m,
                                             double amount, double commissionFee,
                                             String startDate, String endDate, int interval)
          throws IllegalArgumentException {
    if (super.dateCompare(startDate, endDate)) {
      return ("Start date cannot be more than end date!");
    }
    int chk = super.checkPortfolioExists(pname);
    if (chk == 0) {
      super.createFlexiblePortfolio(pname, new ArrayList(), commissionFee);
    }
    dollarCostAveragingHelper(pname, m, amount, commissionFee, startDate, endDate, interval);
    return "Dollar Cost Averaging Portfolio Created Successfully!!";
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
  public boolean loadPersistantStrategy(String pname) {
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
  public StringBuilder getFlexiblePortfolioComposition(String pName, String date) {
    loadPersistantStrategy(pName);
    return super.getFlexiblePortfolioComposition(pName, date);
  }

  @Override
  public StringBuilder getCostBasis(String date, String pName) {
    loadPersistantStrategy(pName);
    return super.getCostBasis(date, pName);
  }

  @Override
  public StringBuilder getFlexiblePortfolioTotalValuation(String date, String pName) {
    loadPersistantStrategy(pName);
    return super.getFlexiblePortfolioTotalValuation(date, pName);
  }

  @Override
  public StringBuilder displayChart(String startDate, String endDate, String pName)
          throws IllegalArgumentException {
    loadPersistantStrategy(pName);
    return super.displayChart(startDate, endDate, pName);
  }
}
