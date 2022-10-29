package stocks;

import java.util.Map;

public interface Portfolio {

  Map<String, Integer> getStockComposition();

  double getValuationAtDate(String date);

  String getName();

  Map<String, Integer> getStocks();
}
