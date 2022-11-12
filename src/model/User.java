package model;

import java.util.List;
import java.util.Map;

/**
 * This interface is the model of user. Here we implement create and load portfolios.
 * As user has a portfolio, it calls an object of portfolio and is able to access its methods.
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
   * @return true if the portfolio exists and false otherwise.
   */

  boolean checkPortfolioExists(String pName);

  void createFlexiblePortfolio(String portfolioName, List<Stocks> stocks) throws IllegalArgumentException;

  StringBuilder getFlexiblePortfolioComposition(String pName);

  String loadFlexiblePortfolio(String fileName) throws IllegalArgumentException;

  String buyStocks(String ticker, int qty, String pName, String date);

  String sellStocks(String ticker, int qty, String pName, String date);

  void display();
  StringBuilder getFlexiblePortfolioTotalValuation(String date, String pName);
}
