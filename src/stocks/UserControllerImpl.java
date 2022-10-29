package stocks;

import static stocks.UserImpl.ifStocksExist;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

  private final Readable in;
  private final Appendable out;
  private User user;
  private View view;

  public UserControllerImpl(Readable in, Appendable out, User user, View view) {
    this.in = in;
    this.out = out;
    this.user = user;
    this.view = view;
  }

  public Map<String, Integer> perform() {

    Map<String, Integer> stocks = new HashMap<>();
    while (true) {
      System.out.println("Enter 1 to add stocks to your portfolio");

      System.out.println("Enter q to exit");
      Scanner scan = new Scanner(this.in);
      switch (scan.next()) {
        case "1" -> {
          System.out.println("Enter ticker of stock you want to add to the portfolio");
          String ticker = scan.next();
          System.out.println("Enter quantity of stocks");
          int qty = scan.nextInt();
          if (ifStocksExist(ticker)) {
            if (!stocks.containsKey(ticker)) {
              stocks.put(ticker, qty);
            } else {
              int temp = stocks.get(ticker);
              stocks.put(ticker, qty + temp);
            }
          } else {
            System.out.println("This ticker does not exist!! Please enter a valid ticker");
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
          Scanner sc = new Scanner(this.in);
          System.out.println("Enter your portfolio name");
          String portfolioName = sc.next();
          user.createPortfolio(portfolioName, perform());
          break;
        case "2":
          StringBuilder composition = user.getPortfolioComposition();
          this.view.viewComposition(composition);
          break;
        case "3":
          Scanner s = new Scanner(System.in);
          System.out.println("Enter the ticker: ");
          String stockSymbol = s.nextLine();
          System.out.println("Enter the date: ");
          String date = s.nextLine();
          if (user.isValidFormat(date)) {
            user.getClosingValue(stockSymbol, date);
          } else {
            System.out.println("Date is not in proper format!!");
          }
          break;

        case "q":
          return;
      }
    }
  }
}
