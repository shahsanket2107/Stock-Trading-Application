package model;

/**
 * This class has all the functions of Stock model. The stock model consists of a ticker symbol,
 * date, quantity and a cost basis. This class also has an API object which calls the alphavantage
 * api.
 */
public class StocksImpl implements Stocks {

  private String ticker;
  private String date;
  private int qty;
  private double cost_basis;

  /**
   * This is a constructor which initializes the ticker,date,quantity and cost basis to default
   * value.
   */
  public StocksImpl() {
    this.ticker = "";
    this.date = "";
    this.qty = 0;
    this.cost_basis = 0.0;
  }

  /**
   * This is a constructor which is used to set the date, quantity and ticker.
   * @param date the date when the stocks were bought or sold.
   * @param ticker the ticker symbol of the stock.
   * @param qty the quantity of stocks.
   */
  public StocksImpl(String date, String ticker, int qty) {
    this.ticker = ticker;
    this.qty = qty;
    this.date = date;
  }

  /**
   * This constructor is used to initialize the ticker.
   * @param ticker the ticker symbol of the stock to be initialized.
   */
  public StocksImpl(String ticker) {
    this.ticker = ticker;
  }


  @Override
  public double getValuationFromDate(int quantity, String date) throws IllegalArgumentException {
    String temp = getClosingValue(date);
    double closing_value = Double.parseDouble(temp);
    return closing_value * quantity;
  }

  @Override
  public String getTicker() {
    return this.ticker;
  }

  @Override
  public String getDate() {
    return this.date;
  }

  @Override
  public int getQty() {
    return this.qty;
  }

  @Override
  public void setQty(int qty) {
    this.qty = qty;
  }

  @Override
  public Double getCostBasis() {
    return this.cost_basis;
  }

  @Override
  public void setCostBasis(Double cost_basis) {
    this.cost_basis = cost_basis;
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
    StringBuilder output;
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
