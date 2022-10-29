package stocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PortfolioImpl implements Portfolio {

  private final String name;
  private final Map<String, Integer> stocks;

  public PortfolioImpl(String name, Map<String, Integer> stocks) {
    this.name = name;
    this.stocks = stocks;
  }

  @Override
  public Map<String, Integer> getStockComposition() {
    return this.stocks;
  }

  @Override
  public double getValuationAtDate(String date) {
    Map<String, Integer> stock = this.stocks;
    List<Double> temp = new ArrayList<>();
    stock.forEach((k, v) -> {
      //temp.add(k.getValuationFromDate(v, date));
    });
    double ans = 0;
    for (double i : temp) {
      ans += i;
    }
    return ans;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public Map<String, Integer> getStocks() {
    return this.stocks;
  }

}
