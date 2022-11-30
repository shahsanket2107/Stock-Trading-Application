package controller;

/**
 * This is a Features interface which has all the methods which we need to display in our GUI menu.
 * This Features interface will be called in IView so that we can add the ActionListeners and call
 * these methods.
 */
public interface Features {

  /**
   * This method is used to create a flexible portfolio of a user. The portfolio name should not
   * exist before and a new portfolio will be created.
   */
  void createPortfolio();

  /**
   * This method is used to buy stocks on a particular date and add them to a particular portfolio.
   * The commission fee is also asked here.
   */
  void buyStocks();

  /**
   * This method is used to load a flexible portfolio. After loading, you can do buy/sell and all
   * other operations on the portfolio.
   */
  void loadPortfolio();

  /**
   * This method is used to sell stocks on a particular date and update a particular portfolio. The
   * commission fee is also asked here.
   */
  void sellStocks();

  /**
   * This method is used to determine the total valuation of a particular portfolio on a particular
   * date.
   */
  void getValuation();

  /**
   * This method is used to determine the total cost basis of a particular portfolio on a particular
   * date. The cost basis is the total amount invested in a particular portfolio which includes the
   * commission fee.
   */
  void getCostBasis();

  /**
   * This method is used to determine the composition of a particular portfolio on a particular
   * date.
   */
  void getComposition();

  /**
   * This method is used to determine the performance of a particular portfolio from a start dte to
   * an end date. This performance is shown in a bar chart so corresponding to the dates, value of
   * portfolio is shown.
   */
  void getPerformance();

  /**
   * This method is used to invest a certain amount entered by the user across a series of stocks
   * based on their fractional weights.
   */
  void investInPortfolio();

  /**
   * This method creates a portfolio (if not exists) or invests in a portfolio (if exists) according
   * to the dollar cost averaging strategy. This method allows the user to not specify an end date
   * which means that the investment is still ongoing.
   */
  void createPortfolioUsingDollarCost();
}
