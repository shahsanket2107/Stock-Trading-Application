package stocks;

import java.util.Map;

public interface User {

  void createPortfolio(String portfolioName, Map<String, Integer> stocks);

  StringBuilder getPortfolioComposition();

  void getValuationAtDate(String date);

  void savePortfolio();

  void loadPortfolio();

  boolean isValidFormat(String value);

  StringBuilder getTotalValuation(String date,String pName);

  boolean ifStocksExist(String ticker);
}
