package model;

import java.util.List;
import java.util.Map;

/**
 * This interface is used for all the file operations. Here all the read and write methods of xml
 * and json are written.
 */
public interface FileOperations {

  /**
   * This function writes the inflexible portfolio to xml file which can be loaded later.
   *
   * @param fileName      the name of file in which you want to store
   * @param portfolioName the name of portfolios in your file
   * @param stocks        The Map consists of ticker and quantity of stocks in your portfolio
   * @throws IllegalArgumentException when there is some error in writing to the file.
   */
  void writeToFile(String fileName, String portfolioName, Map<String, Integer> stocks)
      throws IllegalArgumentException;

  /**
   * This function writes the flexible portfolio to a json file which can be loaded later.
   *
   * @param fileName      the name of file in which you want to store
   * @param portfolioName the name of portfolios in your file
   * @param stocks        the list of stocks consisting the ticker, quantity, date and cost basis of
   *                      stocks.
   * @throws IllegalArgumentException when there is some error in writing to the file.
   */
  void writeToJson(String fileName, String portfolioName, List<Stocks> stocks)
      throws IllegalArgumentException;

  /**
   * This function is used to read the contents of a xml file.
   *
   * @param fName the file name/path with extension to be read
   * @return the list of portfolios which contains the contents of the file
   * @throws IllegalArgumentException if the file name or format is invalid.
   */
  List<Portfolio> readFromFile(String fName) throws IllegalArgumentException;

  /**
   * This function is used to read the contents of a xml file.
   *
   * @param fName the file name/path with extension to be read
   * @return the list of flexible portfolios which contains the contents of the file
   * @throws IllegalArgumentException if the file name or format is invalid.
   */
  List<FlexiblePortfolio> readFromJson(String fName) throws IllegalArgumentException;

  /**
   * This function is used to read a json file and update it while buying and selling stocks.
   *
   * @param fileName      the name of file to be edited.
   * @param portfolioName the name of portfolio where the transaction occurs
   * @param stocks        the list of stocks consisting ticker, quantity, date and cost basis.
   * @throws IllegalArgumentException if the file does not exist or there is error in writing to
   *                                  file.
   */
  void editJson(String fileName, String portfolioName, List<Stocks> stocks)
      throws IllegalArgumentException;
}
