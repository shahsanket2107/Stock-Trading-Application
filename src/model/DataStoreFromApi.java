package model;

import org.codehaus.jackson.JsonNode;

import java.util.Map;
import java.util.Set;

public interface DataStoreFromApi {
  Map<String, JsonNode> getApi_data();
  void fetchFromApi(String ticker) throws IllegalArgumentException;
  Set<String> getTickers();
  void display();
}
