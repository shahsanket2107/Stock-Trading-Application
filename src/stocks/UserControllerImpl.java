package stocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

  private final Readable in;
  private final Appendable out;
  private final User user;
  private final View view;

  public UserControllerImpl(Readable in, Appendable out, User user, View view) {
    this.in = in;
    this.out = out;
    this.user = user;
    this.view = view;
  }

  public Map<String, Integer> perform() {

    Map<String, Integer> stocks = new HashMap<>();
    while (true) {
      view.getAddStockMenu();
      Scanner scan = new Scanner(this.in);
      switch (scan.next()) {
        case "1" -> {

          String ticker = view.getTicker();
          if (user.ifStocksExist(ticker)) {
            ticker = ticker.toUpperCase();
            int qty = view.getQty();
            if (!stocks.containsKey(ticker)) {
              stocks.put(ticker, qty);
            } else {
              int temp = stocks.get(ticker);
              stocks.put(ticker, qty + temp);
            }
          } else {
            view.invalidTicker();
          }
        }
        case "q" -> {
          return stocks;
        }
      }
    }
  }


  @Override
  public void go() {
    Scanner scan = new Scanner(this.in);
    while (true) {
      view.getMenu();
      switch (scan.next()) {
        case "1":
          String portfolioName = view.getPortfolioName();
          user.createPortfolio(portfolioName, perform());
          break;
        case "2":
          StringBuilder composition = user.getPortfolioComposition();
          view.viewComposition(composition);
          break;
        case "3":

          String date = view.getDate();
          if (user.isValidFormat(date)) {
            StringBuilder result = user.getTotalValuation(date);
            view.getPortfolioValue(result);
          } else {
            view.invalidDate();

          }
          break;

        case "q":
          return;
        default:
          view.seeDefault();
      }
    }
  }
}
