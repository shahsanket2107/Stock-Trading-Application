package stocks;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

  private final Readable in;
  private final Appendable out;
  private User user;

  public UserControllerImpl(Readable in, Appendable out, User user) {
    this.in = in;
    this.out = out;
    this.user = user;
  }

  public Map<String, Integer> perform() {

    Map<String, Integer> stocks = new HashMap<>();
    int flag = 0;
    while (true) {
      System.out.println("Enter 1 to add stocks to your portfolio");

      System.out.println("Enter q to exit");
      if (flag == 1) {
        break;
      }
      Scanner scan = new Scanner(this.in);
      switch (scan.next()) {
        case "1" -> {
          System.out.println("Enter ticker of stock you want to add to the portfolio");
          String ticker = scan.next();
          System.out.println("Enter quantity of stocks");
          int qty = scan.nextInt();
          if (user.ifStocksExist(ticker)) {
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
    return stocks;
  }

  @Override
  public void go() {
    Scanner scan = new Scanner(this.in);
    while (true) {
      System.out.println("Enter 1 for making portfolio");
      System.out.println("Enter 2 to examine the composition of portfolio");
      System.out.println("Enter 3 to determine the total value of portfolio on a certain date");
      System.out.println("Enter 4 to save your portfolio");
      System.out.println("Enter 5 to load your portfolio");
      System.out.println("Enter q to exit");
      switch (scan.next()) {
        case "1":
          Scanner sc = new Scanner(this.in);
          System.out.println("Enter your portfolio name");
          String portfolioName = sc.next();
          user.createPortfolio(portfolioName, perform());
          break;
        case "2":
          System.out.println(user.getPortfolioComposition());
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
