package stocks;

import java.util.ArrayList;
import java.util.Map;

public interface User {

  ArrayList<Portfolio> createPortfolio(Map<Stocks, Integer> stocks, String name);

  void getValuationAtDate(String date);

  void savePortfolio();

  void loadPortfolio();
}
