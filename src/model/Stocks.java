package model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * This interface is the model of stock. Here the value of stocks of an inflexible portfolio on a
 * particular date is found. The class has an object of API class which hits the api and fetches
 * value. The stock model previously had ticker and quantity but now date is added to incorporate
 * the flexible portfolios. Also, cost basis is added to this model.
 * JSON Deserialize annotation is used to deserialize the Stocks object which is actually an object
 * of Stocks Interface and not the StocksImpl class. So this annotation tells Jackson that while
 * deserializing the Stocks object read it as a StocksImpl class.
 */
@JsonDeserialize(as = StocksImpl.class)
public interface Stocks {

  /**
   * This function takes the quantity and date as input and hits the alpha vantage api to get the
   * value of ticker on that particular date. The api is in a different class and uses that class's
   * object to call the api
   *
   * @param quantity the quantity of stocks in the portfolio
   * @param date     the date at which you need to find value.
   * @return the value of that particular ticker at the inputted date multiplied by its quantity, so
   * we get the final value of that stock in the portfolio.
   * @throws IllegalArgumentException when the data for entered date does not exist, or if the api
   *                                  is unable to fetch data
   */
  double getValuationFromDate(int quantity, String date) throws IllegalArgumentException;

  /**
   * This is a getter function used to fetch the ticker of stock.
   *
   * @return the ticker of stock
   */
  String getTicker();

  /**
   * This is a getter function used to fetch date.
   *
   * @return the date when the stocks were bought or sold.
   */
  String getDate();

  /**
   * This is a getter function used to fetch quantity.
   *
   * @return the quantity of stocks
   */
  double getQty();

  /**
   * This is a setter function used to set the updated quantity after stocks are bought/sold.
   *
   * @param qty the updated quantity
   */
  void setQty(double qty);

  /**
   * This function is a getter function used to get the cost basis.
   *
   * @return the cost basis
   */
  Double getCostBasis();

  /**
   * This is a setter function used to set the cost basis according to the input.
   *
   * @param costBasis the value which needs to be set
   */
  void setCostBasis(Double costBasis);
}
