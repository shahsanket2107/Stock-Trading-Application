package stocks;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Map;

public class UserImpl implements User {

  String name;
  ArrayList<Portfolio> portfolio;

  public UserImpl(String name) {
    this.name = name;
    this.portfolio = new ArrayList<>();
  }

  @Override
  public ArrayList<Portfolio> createPortfolio(Map<Stocks, Integer> stocks, String name) {
    // Call view and get the names of ticker and quantities of shares of each stock.
    long millis = System.currentTimeMillis();
    Date temp_date = new Date(millis);
    String date = temp_date.toString();
    this.portfolio.add(new PortfolioImpl(name, date, stocks));
    return this.portfolio;
  }

  @Override
  public void getValuationAtDate(String date) {

  }

  @Override
  public void savePortfolio() {

  }

  @Override
  public void loadPortfolio() {

  }
}
