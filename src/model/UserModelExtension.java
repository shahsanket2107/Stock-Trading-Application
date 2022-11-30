package model;

import java.util.Map;

/**
 * This interface extends the User Model interface and has methods to invest amounts in a
 * portfolio according to dollar cost averaging strategy. This interface also allows strategies
 * that have no end date.
 */
public interface UserModelExtension extends User {

  boolean investFractionalPercentage(String pname, String date,
                                     double amount, Map<String, Double> m,
                                     double commissionFee) throws IllegalArgumentException;

  boolean dollarCostAveragingPortfolio(String pname, Map<String, Double> m, double amount,
                                       double commissionFee, String startDate, String endDate,
                                       int interval) throws IllegalArgumentException;

  boolean loadPersistantStrategy(String pname);

  Map<String, Double> showPerformance(String pName, String sDate, String eDate)
          throws IllegalArgumentException;
}
