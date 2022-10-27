package stocks;

import java.io.InputStream;
import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

  private User user_model;
  Stocks stocks;
  Portfolio portfolio;
  private InputStream input;

  public UserControllerImpl(User user_model, InputStream input) {
    this.user_model = user_model;
    this.input = input;
  }

  @Override
  public void go() {
    Map<Stocks, Integer> m = new HashMap<>();
    m.put(new StocksImpl("AAPL"), 3);
    m.put(new StocksImpl("GOOGL"), 2);
    user_model.createPortfolio(m, "p1");
    //System.out.println(m);
  }
}
