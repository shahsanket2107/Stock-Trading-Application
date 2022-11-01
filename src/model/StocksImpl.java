package model;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * This class has all the functions of Stock model. The alpha vantage API is also called in this
 * class to fetch the stock values.
 */
public class StocksImpl implements Stocks {

  private final String ticker;

  public StocksImpl(String ticker) {
    this.ticker = ticker;
  }

  @Override
  public double getValuationFromDate(int quantity, String date) throws IllegalArgumentException {
    String temp = getClosingValue(date);
    double closing_value = Double.parseDouble(temp);
    return closing_value * quantity;
  }

  /**
   * This function hits the api to fetch the closing value of a ticker at a particulare date.
   *
   * @param date the date at which we need to find the value
   * @return the closing value of that ticker on inputted date.
   * @throws IllegalArgumentException if url is unable to fetch data or the data for given date does
   *                                  not exist.
   */
  private String getClosingValue(String date) throws IllegalArgumentException {
    String apiKey = "FHA1IC5A17Q0SPLG";
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
      int index = output.indexOf(date);
      if (index == -1) {
        throw new IllegalArgumentException("Data for given parameter does not exist!!");
      }
      int endIndex = output.indexOf("\n", index);

      String temp = output.substring(index, endIndex);

      String[] res = temp.split(",", 0);

      String closeValue = res[4];
      return closeValue;
    } catch (IOException e) {
      throw new IllegalArgumentException("Unable to fetch URL right now!!");
    }
  }
}
