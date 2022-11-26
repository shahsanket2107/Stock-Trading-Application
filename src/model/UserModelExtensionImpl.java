package model;


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

//  @Override
//  public String investFractionalPercentage(String pname, String date,
//                                           double amount, Map<String, Double> m,
//                                           double commissionFee) throws IllegalArgumentException {
//    List<FlexiblePortfolio> flexiblePortfolioList = super.getFlexiblePortfolioList();
//    double finalAmount = amount - commissionFee;
//    int flg = 0;
//    for (FlexiblePortfolio p : flexiblePortfolioList) {
//      if (p.getName().equals(pname)) {
//        flg = 1;
//        List<String> stocks = setOfStocks(pname, date);
//        double commissionFeePerStock = commissionFee / stocks.size();
//        m.forEach((k, v) -> {
//          for (String s : stocks) {
//            if (s.equalsIgnoreCase(k)) {
//              double temp = getValuationHelper(k, date);
//              double fractionalShare = finalAmount * v / 100.0;
//              double qty = fractionalShare / temp;
//              Stocks tempStock = new StocksImpl(date, s, qty);
//              tempStock.setCostBasis(qty * temp + commissionFeePerStock);
//              p.getStocks().add(tempStock);
//            }
//          }
//        });
//        String fileName = super.getName() + "_portfolios.json";
//        FileOperations write = new FileOperationsImpl();
//        write.editJson(fileName, pname, p.getStocks());
//        break;
//      }
//    }
//    if (flg == 0) {
//      return "Portfolio Name does not exist!!";
//    }
//    return "Amount Invested Successfully!!";
//  }

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

  @Override
  public String createDollarCostAveragingPortfolio(String pname, Map<String, Double> m,
                                                   double amount, double commissionFee,
                                                   String startDate, String endDate, int interval)
          throws IllegalArgumentException {
    if (super.dateCompare(startDate, endDate)) {
      return ("Start date cannot be more than end date!");
    }
    super.createFlexiblePortfolio(pname, new ArrayList(), commissionFee);
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Calendar c = Calendar.getInstance();
    Date s;
    while (super.dateCompare(endDate, startDate)) {
      try {
        s = sdf.parse(startDate);
      } catch (Exception e) {
        throw new IllegalArgumentException("Error in parsing date!!");
      }
      investFractionalPercentage(pname, startDate, amount, m, commissionFee);
      c.setTime(s);
      c.add(Calendar.DATE, interval);
      startDate = sdf.format(c.getTime());
    }
    return "Dollar Cost Averaging Portfolio Created Successfully!!";
  }
}
