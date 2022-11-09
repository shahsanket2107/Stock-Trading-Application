package model;

import java.util.List;
import java.util.Map;

public interface FileOperations {

  void writeToFile(String fileName, String portfolioName, Map<String, Integer> stocks)
      throws IllegalArgumentException;

  List<Portfolio> readFromFile(String pfName) throws IllegalArgumentException;
}