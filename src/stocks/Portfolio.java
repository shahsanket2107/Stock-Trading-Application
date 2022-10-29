package stocks;

import java.util.Map;

public interface Portfolio {

  double getValuationAtDate(String date);

  String getName();

  Map<String, Integer> getStocks();
}
