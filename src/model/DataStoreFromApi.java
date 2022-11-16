package model;

import org.codehaus.jackson.JsonNode;

import java.util.Map;
import java.util.Set;

/**
 * This interface contains methods to fetch,store and display the AlphaVantage API results,
 * to reduce the number of API calls.
 */
public interface DataStoreFromApi {
  /**
   * This method is used to get the api data which is stored in the instance variable of map.
   *
   * @return the map of String and JSONNode which maps the ticker of a stock to it's JSON Node
   * output of the API call.
   */
  Map<String, JsonNode> getApi_data();

  /**
   * This method is used to fetch data from the API and store it in the Map of String and JSON Node.
   *
   * @param ticker is the ticker of the stock whose API we want to call.
   * @throws IllegalArgumentException if there is some error in fetching data from the API.
   */
  void fetchFromApi(String ticker) throws IllegalArgumentException;

  /**
   * This method is used to get the set of all tickers that have already been processed and
   * stored to minimize the number of API calls.
   *
   * @return the set of tickers that have already been processed.
   */
  Set<String> getTickers();
}
