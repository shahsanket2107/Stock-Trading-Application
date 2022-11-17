package model;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the class of Flexible portfolio which consists of methods to get portfolios and get and
 * set stocks.
 */
public class FlexiblePortfolioImpl implements FlexiblePortfolio {

  private String name;
  private List<Stocks> stocks;

  /**
   * The non parameters constructor initializes the name of portfolio and stocks as default.
   */
  public FlexiblePortfolioImpl() {
    this.name = "portfolio";
    this.stocks = new ArrayList<>();
  }

  /**
   * This constructor is used to initialize the name and list of stocks.
   *
   * @param name   the name of portfolio.
   * @param stocks the list of stocks consisting ticker, quantity and date.
   */
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
  public void setStocks(List<Stocks> stocks) {
    this.stocks = stocks;
  }
}
