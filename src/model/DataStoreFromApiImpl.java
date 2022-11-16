package model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * This class implements the DataStoreFromApi interface. This class is made for the purpose to
 * reduce the number of API calls to the minimum number possible. So each time a new stock ticker
 * is encountered the data is fetched for that ticker from the API and stored in a Map which maps
 * ticker to the JSON output of the API.
 */
public class DataStoreFromApiImpl implements DataStoreFromApi {
  private Map<String, JsonNode> api_data;

  /**
   * This is the default constructor which initializes the api_data map with a new Hashmap.
   */
  public DataStoreFromApiImpl() {
    this.api_data = new HashMap<>();
  }

  @Override
  public Map<String, JsonNode> getApi_data() {
    return this.api_data;
  }

  @Override
  public void fetchFromApi(String ticker) throws IllegalArgumentException {
    String apiKey = "UN3KWVY13HBXYSVR";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=json");
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Malformed URL Exception!!");
    }

    InputStream in;

    try {
      assert url != null;
      in = url.openStream();
      ObjectMapper mapper = new ObjectMapper();
      JsonNode jsonMap = mapper.readTree(in);
      JsonNode array = jsonMap.get("Time Series (Daily)");
      api_data.put(ticker, array);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error in fetching data from API!!");
    }
  }

  @Override
  public Set<String> getTickers() {
    return api_data.keySet();
  }

}
