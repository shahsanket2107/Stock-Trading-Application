package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class ApiImpl implements Api{

  @Override
  public StringBuilder getApiOutputFromTicker(String ticker) {
    String apiKey = "UN3KWVY13HBXYSVR";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new IllegalArgumentException("Malformed URL Exception!!");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();
    try {
      assert url != null;
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      return output;
    }
    catch (IOException e) {
      throw new IllegalArgumentException("Unable to fetch URL right now!!");
    }
  }
}
