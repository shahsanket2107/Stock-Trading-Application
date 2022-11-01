package view;

import java.io.PrintStream;

public interface View {


  /**
   * This function prints the menu which gives user options to choose.
   */

  void getMenu();

  /**
   * This function prints menu to add stocks or return to main menu.
   */
  void getAddStockMenu();

  /**
   * This function prints appropriate Input ticker message.
   */
  void getTicker();

  /**
   * This function prints appropriate input quantity message.
   */
  void getQty();

  /**
   * This function prints appropriate message if quantity passed is negative.
   */

  void qtyPositive();

  /**
   * This function prints appropriate message if quantity passed is not an integer.
   */
  void qtyInteger();

  /**
   * This function displays error message if the inputted ticker is invalid.
   */
  void invalidTicker();

  /**
   * This function displays the name of portfolio as input from user.
   */

  void getPortfolioName();

  /**
   * This function displays appropriate message if the portfolio name is empty.
   */
  void emptyPortfolioMessage();

  /**
   * This function displays appropriate message while taking the file name/path as input from the
   * user.
   */
  void getFileName();

  /**
   * This function displays appropriate message while taking the date as input from the user.
   */
  void getDate();

  /**
   * This function displays the message which needs to displayed
   *
   * @param message is the message which needs to be displayed.
   */

  void displayMessage(String message);


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
   * This function is used to take name of the user as input.
   */
  void getName();

  /**
   * This function displays the message after the user enters his name.
   */

  void displayName(String name);

  /**
   * This function displays the message if the portfolio already exists.
   */
  void alreadyExists();

  /**
   * This function is a setter method which sets the PrintStream object
   *
   * @param out an object of PrintStream.
   */
  void setStream(PrintStream out);

}
