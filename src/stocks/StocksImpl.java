package stocks;

public class StocksImpl implements Stocks {
  final String ticker;
  final double closing_value;

  public StocksImpl(String ticker, double closing_value) {
    this.ticker = ticker;
    this.closing_value = closing_value;
  }

  @Override
  public void getValuationFromDate(int quantity, String date) {

  }
}
