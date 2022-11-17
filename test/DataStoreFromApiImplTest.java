import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import model.DataStoreFromApi;
import model.DataStoreFromApiImpl;

import static org.junit.Assert.assertEquals;

/**
 * This class represents the test class for testing the model DataStoreApiImpl. It tests that the
 * alpha vantage API is able to call and store APIs successfully.
 */
public class DataStoreFromApiImplTest {

  @Test
  public void testFetchFromApi() {
    DataStoreFromApi data = new DataStoreFromApiImpl();
    data.fetchFromApi("aapl");
    assertEquals(true, data.getApi_data().containsKey("aapl"));
  }

  @Test
  public void testInvalidTickerFetchFromApi() {
    DataStoreFromApi data = new DataStoreFromApiImpl();
    data.fetchFromApi("test");
    assertEquals(null, data.getApi_data().get("test"));
  }

  @Test
  public void testGetTickers() {
    DataStoreFromApi data = new DataStoreFromApiImpl();
    data.fetchFromApi("aapl");
    data.fetchFromApi("googl");
    data.fetchFromApi("tsla");
    Set<String> s = new HashSet<>();
    s.add("aapl");
    s.add("googl");
    s.add("tsla");
    assertEquals(s, data.getTickers());
  }

}