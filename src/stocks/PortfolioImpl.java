package stocks;

import java.util.HashMap;
import java.util.Map;

public class PortfolioImpl implements Portfolio {

  Map<Stocks, Integer> stocks = new HashMap<>();
  final String date;
  final String name;

  public PortfolioImpl(String date, String name) {
    this.date = date;
    this.name = name;
  }

  @Override
  public void getStockComposition() {

  }

  @Override
  public void getValuationAtDate() {

  }

  @Override
  public void showInvestmentAmount() {

  }
}
