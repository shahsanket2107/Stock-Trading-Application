package model;

import java.util.ArrayList;
import java.util.List;

public class FlexiblePortfolioImpl implements FlexiblePortfolio {

  private String name;
  private List<Stocks> stocks;

  public FlexiblePortfolioImpl() {
    this.name = "John Doe";
    this.stocks = new ArrayList<>();
  }

  public FlexiblePortfolioImpl(String name, List<Stocks> stocks) {
    this.name = name;
    this.stocks = stocks;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public List<Stocks> getStocks() {
    return this.stocks;
  }

  @Override
  public void setStocks(List<Stocks> stocks){
    this.stocks = stocks;
  }
}
