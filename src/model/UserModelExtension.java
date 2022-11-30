package model;

import java.util.Map;

/**
 * This interface extends the User Model interface and has methods to invest amounts in a
 * portfolio according to dollar cost averaging strategy. This interface also allows strategies
 * that have no end date.
 */
public interface UserModelExtension extends User {

  /**
   * This method is used to invest a certain amount entered by the user across a series of stocks
   * based on their fractional weights.
   *
   * @param pname         is the name of the portfolio where user wants to invest the amount.
   * @param date          is the date at which user wants to invest the amount.
   * @param amount        is the amount the user wants to invest.
   * @param m             is the map of the fractional distribution of stocks which maps the ticker
   *                      of stocks to the fractional percentage to be invested.
   * @param commissionFee is the commission fee charged per transaction.
   * @return true if the investment is successful without any errors.
   * @throws IllegalArgumentException if portfolio name does not exist, if date is not properly formatted,
   *                                  if the date is in the future, if amount in negative, if commissionFee is negative, if the sum
   *                                  of fractional distribution of stocks does not sum up to be 100%.
   */
  boolean investFractionalPercentage(String pname, String date,
                                     double amount, Map<String, Double> m,
                                     double commissionFee) throws IllegalArgumentException;

  /**
   * This method creates a portfolio (if not exists) or invests in a portfolio (if exists) according
   * to the dollar cost averaging strategy. This method allows the user to not specify an end date
   * which means that the investment is still ongoing.
   *
   * @param pname         is the name of portfolio to be created/ invested in according to dollar cost
   *                      averaging strategy.
   * @param m             is the map of the fractional distribution of stocks which maps the ticker
   *                      of stocks to the fractional percentage to be invested.
   * @param amount        is the amount the user wants to invest.
   * @param commissionFee is the commission fee charged per transaction.
   * @param startDate     is the date from which user wants to start investing in his portfolio.
   * @param endDate       is the date at which he wants to end investing in his portfolio.
   * @param interval      is the frequency in days at which he wants to make periodic investments in his
   *                      portfolio.
   * @return true if a portfolio is created successfully according to dollar cost averaging method.
   * @throws IllegalArgumentException if dates are not properly formatted, if interval is negative,
   *                                  if amount in negative, if commissionFee is negative, if the sum
   *                                  of fractional distribution of stocks does not sum up to be 100%.
   */
  boolean dollarCostAveragingPortfolio(String pname, Map<String, Double> m, double amount,
                                       double commissionFee, String startDate, String endDate,
                                       int interval) throws IllegalArgumentException;

  /**
   * This method is used to load the persistant strategy which was persisted because the amount was
   * to be invested in the future. So this method loads that strategy and compares the start date
   * of that strategy with the current date and if the start date is less than the current date, it
   * makes the investment for the user, updates the user portfolio and also updates the start date
   * of the persisted strategy.
   *
   * @param pname is the name of the portfolio which is to be loaded.
   * @return true if the strategy is loaded and all the operations are performed successfully on it
   * without any error.
   * @throws IllegalArgumentException if there is some problem reading from and writing to the file.
   */
  boolean loadPersistantStrategy(String pname) throws IllegalArgumentException;

  /**
   * This method is used to display performace of a particular portfolio over a period of time.
   *
   * @param pName is the name of the portfolio whose performance is to be viewed.
   * @param sDate is the start date at which user wants to start viewing the portfolio performance.
   * @param eDate is the end date at which user wants to stop viewing the portfolio performance.
   * @return the map containing the date mapping to the portfolio valuation at that date.
   * @throws IllegalArgumentException if date format is improper or if start date is greater than
   *                                  the end date.
   */
  Map<String, Double> showPerformance(String pName, String sDate, String eDate)
          throws IllegalArgumentException;
}
