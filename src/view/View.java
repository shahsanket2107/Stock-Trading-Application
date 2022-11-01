package view;

public interface View {

  /**
   * This function views the composition of the portfolio.
   *
   * @param pName the portfolio name whose composition you want to see.
   */
  void viewComposition(StringBuilder pName);

  /**
   * This function prints the menu which gives user options to choose.
   */

  void getMenu();

  /**
   * This function gives user option to add stocks or return to main menu.
   */
  void getAddStockMenu();

  /**
   * This function takes the ticker symbol of stock as an input from user.
   *
   * @return the ticker which user inputted.
   */
  String getTicker();

  /**
   * This function takes the quantity of stocks as input from user.
   *
   * @return the quantity which user inputted.
   */
  int getQty();

  /**
   * This function displays error message if the inputted ticker is invalid.
   */
  void invalidTicker();

  /**
   * This function takes the name of portfolio as input from user.
   *
   * @return the name of portfolio which user inputted.
   */

  String getPortfolioName();

  /**
   * This function takes the file name/path as input from the user.
   *
   * @return the file name/path which user inputted
   */
  String getFileName();

  /**
   * This function takes the date as input from the user.
   *
   * @return the date which user inputted.
   */
  String getDate();

  /**
   * This function displays all the portfolio names
   *
   * @param result is the string consisting all the portfolio names which need to be displayed.
   */

  void displayResult(StringBuilder result);

  /**
   * This function is used to display exception if the portfolio to be loaded does not exist.
   *
   * @param exception the message which needs to be displayed.
   */
  void displayExceptions(String exception);

  /**
   * This function is used to print out a message if the date is not in proper format.
   */

  void invalidDate();

  /**
   * This function displays the message when user inputs any key which is not mentioned in the
   * menu.
   */

  void seeDefault();

  /**
   * This function displays the message when portfolio is successfully loaded.
   *
   * @param output the message which needs to be displayed
   */

  void getLoadPortfolio(String output);

  /**
   * This function is used to take name of the user as input.
   *
   * @return the name which is inputted by the user
   */
  String getName();

  /**
   * This function displays the message after the user enters his name.
   *
   * @param name the name of the user
   */

  void displayName(String name);

  /**
   * This function displays the message if the portfolio already exists.
   */
  void alreadyExists();

}
