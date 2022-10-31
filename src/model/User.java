package model;

import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;

public interface User {

  void createPortfolio(String portfolioName, Map<String, Integer> stocks) throws IllegalArgumentException;

  StringBuilder getPortfolioComposition(String pName);

  String loadPortfolio(String pfName) throws IllegalArgumentException;

  boolean isValidFormat(String value);

  StringBuilder getTotalValuation(String date, String pName);

  boolean ifStocksExist(String ticker);

  String getName();

  void setName(String name);

  StringBuilder getPortfoliosName();

  boolean checkPortfolioExists(String pName);
}
