package model;

import java.util.Map;

public interface User {

  void createPortfolio(String portfolioName, Map<String, Integer> stocks);

  StringBuilder getPortfolioComposition();

  String loadPortfolio(String pfName);

  boolean isValidFormat(String value);

  StringBuilder getTotalValuation(String date, String pName);

  boolean ifStocksExist(String ticker);

  void setName(String name);

  String getName();
}
