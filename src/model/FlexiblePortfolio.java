package model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

/**
 * This interface is a model of flexible portfolio. The flexible portfolio consists of a list of
 * stocks which has a ticker, a quantity ,a date and cost basis. Since the inflexible portfolios did
 * not need dates and cost basis as there was no buying and selling of stocks but we needed it here,
 * so we made a new model.
 * <p>
 * JSON Deserialize annotation is used to deserialize the Stocks object which is actually an object
 * of Stocks Interface and not the StocksImpl class. So this annotation tells Jackson that while
 * deserializing the Stocks object read it as a StocksImpl class.
 */
@JsonDeserialize(as = FlexiblePortfolioImpl.class)
public interface FlexiblePortfolio {

  /**
   * This function is a getter to get the name of flexible portfolio.
   *
   * @return the name of the flexible portfolio
   */
  String getName();

  /**
   * This function is a getter to get the list of stocks consisting ticker, date, quantity and cost
   * basis.
   *
   * @return the list of stocks in the portfolio.
   */
  List<Stocks> getStocks();

  /**
   * This function is a setter used to set the value of list of stocks. This is because while using
   * the buy and sell stocks, we will need to set the updated value.
   *
   * @param stocks the list of stocks which need to be set.
   */
  void setStocks(List<Stocks> stocks);
}
