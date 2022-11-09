package model;

import java.util.List;
import java.util.Map;
import org.json.JSONArray;

public interface FileOperations {

  void writeToFile(String fileName, String portfolioName, Map<String, Integer> stocks)
      throws IllegalArgumentException;
  void writeToJson(String fileName,String  portfolioName, JSONArray jsonArray)
    throws IllegalArgumentException;

  List<Portfolio> readFromFile(String pfName) throws IllegalArgumentException;
}
