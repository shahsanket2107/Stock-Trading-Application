package stocks;

import java.util.Map;

public interface Portfolio {

  Map<Stocks, Integer> getStockComposition();

  double getValuationAtDate(String date);

  double showInvestmentAmount();
}
