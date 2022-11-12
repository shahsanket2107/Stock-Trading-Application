package model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

/**
 * This interface is the model of stock. Here the Alpha Vantage api is called and the value of
 * stocks on a particular date is found.
 */
@JsonDeserialize(as = StocksImpl.class)
public interface Stocks {

  /**
   * This function takes the quantity and date as input and hits the alpha vantage api to get the
   * value of ticker on that particular date.
   *
   * @param quantity the quantity of stocks in the portfolio
   * @param date     the date at which you need to find value.
   * @return the value of that particular ticker at the inputted date multiplied by its quantity, so
   * we get the final value of that stock in the portfolio.
   * @throws IllegalArgumentException when the data for entered date does not exist, or if the api
   *                                  is unable to fetch data
   */
  double getValuationFromDate(int quantity, String date) throws IllegalArgumentException;

  String getTicker();

  String getDate();

  int getQty();

  void setQty(int qty);

  Double getCostBasis();

  void setCostBasis(Double cost_basis);
}
