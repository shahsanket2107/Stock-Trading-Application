package view;

import controller.Features;
import java.util.ArrayList;
import java.util.Map;

/**
 * This is the interface for the GUI view. All the output to be shown in the GUI including making
 * the JFame and showing pop-ups everything will be done in this class.
 */
public interface guiView {

  /**
   * This method is used to set the action listeners of the buttons and call the corresponding
   * methods in Controller which are in Features interface.
   *
   * @param features Object of Features interface which consists of all the features our GUI
   *                 provides.
   */
  void addFeatures(Features features);

  /**
   * This method displays the message as an output dialog box.
   *
   * @param message the message to be displayed.
   */
  void showOutput(String message);

  /**
   * This method is used to take inputs from the user using input dialog box required for create,
   * buy and sell portfolio methods.
   *
   * @return an output array consisting of all the inputs taken from the user, so it can be fetched
   *         from the controller.
   * @throws IllegalArgumentException if input fields are blank
   */
  ArrayList<String> createPortfolioInput() throws IllegalArgumentException;

  /**
   * This method is used to take inputs from the user using input dialog box required for getValue,
   * getCostBasis and getComposition methods.
   *
   * @return an output array consisting of all the inputs taken from the user, so it can be fetched
   *         from the controller.
   * @throws IllegalArgumentException if input fields are blank
   */
  ArrayList<String> getInput() throws IllegalArgumentException;

  /**
   * This method is used to take inputs from the user using input dialog box required for
   * getPortfolioPerformance method.
   *
   * @return an output array consisting of all the inputs taken from the user, so it can be fetched
   *         from the controller.
   * @throws IllegalArgumentException if input fields are blank
   */
  ArrayList<String> getInputForPerformance() throws IllegalArgumentException;

  /**
   * This method is used to take inputs from the user using input dialog box required for
   * investInPortfolio method.
   *
   * @return an output array consisting of all the inputs taken from the user, so it can be fetched
   *         from the controller.
   * @throws IllegalArgumentException if input fields are blank
   */
  ArrayList<String> getInvestmentDetails() throws IllegalArgumentException;

  /**
   * This function take number of stocks as a parameter and takes input of that many stock's ticker
   * and percentage proportion to be invested.
   *
   * @param num the number of stocks you want to invest in.
   * @return a map consisting of tickers of stocks and their percentage proportion.
   * @throws IllegalArgumentException if the fields are blank.
   */
  Map<String, Double> getInvestmentShares(int num) throws IllegalArgumentException;

  /**
   * This method is used to take inputs from the user using input dialog box required for finding
   * dollar cost.
   *
   * @return an output array consisting of all the inputs taken from the user, so it can be fetched
   *         from the controller.
   * @throws IllegalArgumentException if input fields are blank
   */
  ArrayList<String> getDollarCostDetails() throws IllegalArgumentException;

  /**
   * This method displays appropriate message in form of output dialog box if fields are blank.
   */
  void showBlank();

  /**
   * This method shows the performance of a portfolio in form of a bar chart.
   * @param m map consisting of dates and value of portfolio at that date.
   * @param pName the name of portfolio.
   * @param sDate the start date from which you want to check performance.
   * @param eDate the end date till which you want to check performance.
   */
  void showChart(Map<String, Double> m, String pName, String sDate, String eDate);
}
