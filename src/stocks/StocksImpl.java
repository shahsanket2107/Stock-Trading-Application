package stocks;

public class StocksImpl implements Stocks {
  final String ticker;

  public StocksImpl(String ticker) {
    this.ticker = ticker;
  }

  @Override
  public double getValuationFromDate(int quantity, String date) {
    double closing_value = fetch_closing_value_from_date(date);
    return closing_value * quantity;
  }

  private double fetch_closing_value_from_date(String date) {
    double value = 105.45;
    return value;
  }
}
