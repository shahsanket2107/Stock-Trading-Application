package stocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PortfolioImpl implements Portfolio {

  final String date;
  final String name;
  final Map<Stocks, Integer> stocks;

  public PortfolioImpl(String date, String name, Map<Stocks, Integer> stocks) {
    this.date = date;
    this.name = name;
    this.stocks = stocks;
  }

  @Override
  public Map<Stocks, Integer> getStockComposition() {
    return this.stocks;
  }

  @Override
  public double getValuationAtDate(String date) {
    Map<Stocks, Integer> stock = this.stocks;
    List<Double> temp = new ArrayList<>();
    stock.forEach((k, v) -> {
      temp.add(k.getValuationFromDate(v, date));
    });
    double ans = 0;
    for (double i : temp) {
      ans += i;
    }
    return ans;
  }

  @Override
  public double showInvestmentAmount() {
    return getValuationAtDate(this.date);
  }
}
