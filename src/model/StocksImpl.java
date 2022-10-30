package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

public class StocksImpl implements Stocks {
  final String ticker;

  public StocksImpl(String ticker) {
    this.ticker = ticker;
  }

  @Override
  public double getValuationFromDate(int quantity, String date) throws IOException {
    String temp = getClosingValue(date);
    double closing_value = Double.valueOf(temp);
    return closing_value * quantity;
  }

  private String getClosingValue(String date) throws IOException, IllegalArgumentException {
    String apiKey = "FHA1IC5A17Q0SPLG";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
              + ".co/query?function=TIME_SERIES_DAILY"
              + "&outputsize=full"
              + "&symbol"
              + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      throw new MalformedURLException("Malformed URL Exception!!");
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
      int index = output.indexOf(date);
      if (index == -1) {
        throw new IllegalArgumentException("Data for given date does not exist!!");
      }
      int endIndex = output.indexOf("\n", index);

      String temp = output.substring(index, endIndex);

      String[] res = temp.split(",", 0);

      String closeValue = res[4];
      return closeValue;
    } catch (IOException e) {
      return "Unable to fetch URL right now!!";
    }
  }
}
