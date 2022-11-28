package model;

import java.util.List;
import java.util.Map;

public interface UserModelExtension extends User {
  String investFractionalPercentage(String pname, String date,
                                    double amount, Map<String, Double> m,
                                    double commissionFee) throws IllegalArgumentException;

  String dollarCostAveragingPortfolio(String pname, Map<String, Double> m, double amount,
                                      double commissionFee, String startDate, String endDate,
                                      int interval) throws IllegalArgumentException;

  boolean loadPersistantStrategy(String pname);
}
