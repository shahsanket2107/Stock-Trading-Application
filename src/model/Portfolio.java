package model;

import java.util.Map;

/**
 * This interface is a model of Portfolio.
 */
public interface Portfolio {

  /**
   * This function takes the date as input from the user and returns the value of his portfolio at
   * that particular date. Here we define a stock object and call its getValuationFromDate method
   * which calls the AlphaVantage api and fetches the closing value of stocks.
   *
   * @param date the date at which you want to find the value
   * @return a map containing the stock ticker mapped to it's value based on the quantity at that
   * date.
   */
  Map<String, Double> getValuationAtDate(String date);

  /**
   * This function returns the name of portfolio.
   *
   * @return the name of portfolio
   */
  String getName();

  /**
   * This function is used to get stock's ticker and quantity which is stored in a map.
   *
   * @return the map consisting of Ticker and quantity.
   */
  Map<String, Integer> getStocks();
}
