package model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DataStoreFromApiImpl implements DataStoreFromApi {
  private Map<String, JsonNode> api_data;

  public DataStoreFromApiImpl() {
    this.api_data = new HashMap<>();
  }

  @Override
  public Map<String, JsonNode> getApi_data() {
    return this.api_data;
  }

  @Override
  public void fetchFromApi(String ticker) throws IllegalArgumentException{
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

    InputStream in = null;

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
}
