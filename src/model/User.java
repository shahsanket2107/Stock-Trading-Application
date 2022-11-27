package model;

import java.util.List;
import java.util.Map;

/**
 * This interface is the model of user. Here we implement create and load portfolios. As user has a
 * portfolio, it calls an object of portfolio and is able to access its methods. In part - 2 of the
 * assignments all the additional features that are possible for a flexible portfolio like buying,
 * selling of stocks, getting valuation,cost basis etc. are also added.
 */
public interface User {

  /**
   * This function generates the portfolio of the user in form of a xml file.
   *
   * @param portfolioName the name of portfolio.
   * @param stocks        a map consisting of ticker symbol and quantity.
   * @throws IllegalArgumentException if there is some error in writing to a xml file.
   */
  void createPortfolio(String portfolioName, Map<String, Integer> stocks)
          throws IllegalArgumentException;

  /**
   * This function gives the composition of portfolio in terms of portfolio name, stock ticker and
   * it's quantity.
   *
   * @param pName name of the portfolio
   * @return the composition of portfolio as stated above.
   */
  StringBuilder getPortfolioComposition(String pName);

  /**
   * This function is used to load the portfolio(xml file) and read its content.
   *
   * @param pfName the file name or file path which needs to e loaded.
   * @return the message whether the portfolio got loaded successfully or not.
   * @throws IllegalArgumentException if the file has a wrong format or is unable to read the file.
   */
  String loadPortfolio(String pfName) throws IllegalArgumentException;

  /**
   * This function checks if the date is in correct format (yyyy-MM-dd).
   *
   * @param value the date which needs to be checked
   * @return true if the date is in correct format and false otherwise.
   */

  boolean isValidFormat(String value);

  /**
   * This function gets the total value of entire portfolio on a particular date. It has a portfolio
   * object and calls its function inside.
   *
   * @param date  the date at which you need to find value of portfolio.
   * @param pName the name of the portfolio.
   * @return the total valuation of portfolio on inputted date.
   */

  StringBuilder getTotalValuation(String date, String pName);

  /**
   * This function checks if the ticker is valid or not. It checks if the ticker is defined in the
   * enum.
   *
   * @param ticker the ticker which need to be checked.
   * @return true if the ticker is present in the enum and false otherwise.
   */
  boolean ifStocksExist(String ticker);

  /**
   * This function is used to get the name of the user.
   *
   * @return the name of the user.
   */
  String getName();

  /**
   * This function is used to set the name of the user.
   *
   * @param name the name of user which needs to be set.
   */

  void setName(String name);

  /**
   * This function returns the names of all the portfolios of a particular user.
   *
   * @return the names of all the portfolios of a particular user.
   */

  StringBuilder getPortfoliosName();

  /**
   * This function checks if a portfolio with the given name already exists or not.
   *
   * @param pName the name of portfolio which you need to check.
   * @return 1 if the portfolio is present as an inflexible portfolio and return 2 is the portfolio
   * is present as a flexible portfolio.
   */

  int checkPortfolioExists(String pName);

  /**
   * This method is used to create a Flexible portfolio. After the portfolio is created
   * successfully. It is written to file as well as stored locally.
   *
   * @param portfolioName the name of the portfolio.
   * @param stocks        the list of stocks with all the data needed to create a flexible
   *                      portfolio.
   * @param commissionFee is the commission fee for each transaction.
   * @throws IllegalArgumentException when the portfolio name already exists or if there is some
   *                                  error encountered while writing to the file.
   */
  void createFlexiblePortfolio(String portfolioName, List<Stocks> stocks, double commissionFee)
          throws IllegalArgumentException;

  /**
   * This method is used to get the composition of a flexible portfolio.
   *
   * @param pName is the portfolio name of the flexible portfolio whose composition user wishes to
   *              see.
   * @param date  defines the date at the point of which the user can view the net composition of
   *              his portfolio till that date.
   * @return the String Builder with the portfolio composition till that date.
   */
  StringBuilder getFlexiblePortfolioComposition(String pName, String date);

  /**
   * This method loads the Flexible Portfolio from the .json file.
   *
   * @param fileName is the name of the file to be loaded.
   * @return the String with appropriate success/failure message on loading of the portfolio.
   * @throws IllegalArgumentException when there is some error is loading the portfolio from the
   *                                  file.
   */
  String loadFlexiblePortfolio(String fileName) throws IllegalArgumentException;

  /**
   * This method is used to buy stocks in a particular flexible portfolio.
   *
   * @param ticker        is the stock ticker which is to be bought.
   * @param qty           is the quantity of stocks to be bought.
   * @param pName         is the name of the portfolio in which the user wants to buy the stocks.
   * @param date          is the date at which he wishes to buy the stocks.
   * @param commissionFee is the commission fee for each transaction.
   * @return the appropriate message of successful/unsuccessful buying of stocks.
   */
  String buyStocks(String ticker, double qty, String pName, String date, double commissionFee);

  /**
   * This method is used to sell stocks in a particular flexible portfolio.
   *
   * @param ticker        is the stock ticker which is to be sold.
   * @param qty           is the quantity of stocks to be sold.
   * @param pName         is the name of the portfolio in which the user wants to sell the stocks.
   * @param date          is the date at which he wishes to sell the stocks.
   * @param commissionFee is the commission fee for each transaction.
   * @return the appropriate message of successful/unsuccessful selling of stocks.
   */
  String sellStocks(String ticker, double qty, String pName, String date, double commissionFee);

  /**
   * This method is used to get total valuation of Flexible Portfolio on a particular date.
   *
   * @param date  is the date at which user would like to determine the total portfolio valuation.
   * @param pName is the name of the portfolio whose valuation is to be determined.
   * @return the valuation of the portfolio with the valuation breakdown of each stock.
   */
  StringBuilder getFlexiblePortfolioTotalValuation(String date, String pName);

  /**
   * This method determines if data for a particular date exists in the API call result or not.
   *
   * @param ticker is the stock ticker.
   * @param date   is the date at which data is to be checked.
   * @return true if data exists and false if it does not exist.
   */
  boolean validateDateAccToApi(String ticker, String date);

  /**
   * This method computes the cost basis for a particular portfolio till that date.
   *
   * @param date  is the date till which the cost basis is to be computed.
   * @param pName is the name of the portfolio.
   * @return the cost basis for that portfolio.
   */
  StringBuilder getCostBasis(String date, String pName);

  /**
   * This method displays the chart for the performance of portfolio over a period of time.
   *
   * @param sDate is the start date for plotting portfolio performance.
   * @param eDate is the end date for plotting the portfolio performance.
   * @param pName is the name of the portfolio.
   * @return the chart for the portfolio performance.
   * @throws IllegalArgumentException if there is any error in parsing date.
   */
  StringBuilder displayChart(String sDate, String eDate, String pName) throws
          IllegalArgumentException;

  /**
   * This method is a getter for flexible portfolios list.
   *
   * @return the list of flexible portfolios created by the user.
   */
  List<FlexiblePortfolio> getFlexiblePortfolioList();

  /**
   * This method is used to get the data stored from API for a user.
   *
   * @return the data store object that stored that data.
   */
  DataStoreFromApi getDataStore();
}
