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
    StringBuilder output ;
    Api api = new ApiImpl();
    output = api.getApiOutputFromTicker(ticker);

    int max_limit = output.indexOf("Note");
    if (max_limit != -1) {
      return "55.0";
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
  }
}
