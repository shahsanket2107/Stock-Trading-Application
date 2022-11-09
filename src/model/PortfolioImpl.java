package model;

import java.util.HashMap;
import java.util.Map;

/**
 * This class has all the functions of Portfolio model. The portfolio contains stocks, so this class
 * also uses stock object to call Stock methods.
 */

public class PortfolioImpl implements Portfolio {

  private  String name;
  private  Map<String, Integer> stocks;

  /**
   * This is the constructor for a portfolio which initializes name and map of stocks.
   *
   * @param name   the name of portfolio.
   * @param stocks the map consisting of ticker and quantity.
   */

  public PortfolioImpl(String name, Map<String, Integer> stocks) {
    this.name = name;
    this.stocks = stocks;
  }

  @Override
  public Map<String, Double> getValuationAtDate(String date) throws IllegalArgumentException {
    Map<String, Integer> stock = this.stocks;
    Map<String, Double> m = new HashMap<>();
    stock.forEach((k, v) -> {
      Stocks s = new StocksImpl(k);
      double ans = s.getValuationFromDate(v, date);
      m.put(k, ans);
    });
    return m;
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
