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
   * @param fileName the name of file in which you want to store
   * @param portfolioName the name of portfolios in your file
   * @param stocks The Map consists of ticker and quantity of stocks in your portfolio
   * @throws IllegalArgumentException when there is some error in writing to the file.
   */
  void writeToFile(String fileName, String portfolioName, Map<String, Integer> stocks)
      throws IllegalArgumentException;

  /**
   * This function writes the flexible portfolio to a json file which can be loaded later.
   * @param fileName the name of file in which you want to store
   * @param portfolioName the name of portfolios in your file
   * @param stocks the list of stocks consisting the ticker, quantity and date of stocks.
   * @throws IllegalArgumentException when there is some error in writing to the file.
   */
  void writeToJson(String fileName, String portfolioName, List<Stocks> stocks)
      throws IllegalArgumentException;

  List<Portfolio> readFromFile(String pfName) throws IllegalArgumentException;

  List<FlexiblePortfolio> readFromJson(String pfName) throws IllegalArgumentException;

  void editJson(String fileName, String portfolioName, List<Stocks> stocks)
      throws IllegalArgumentException;
}
