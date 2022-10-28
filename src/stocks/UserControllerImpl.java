package stocks;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class UserControllerImpl implements UserController {

  private User user_model;
  Stocks stocks;
  Portfolio portfolio;
  private InputStream input;
  final Readable in;
  final Appendable out;

  public UserControllerImpl(Readable in, Appendable out, User user_model) {
    this.in = in;
    this.out = out;
    this.user_model = user_model;
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
          Map<Stocks, Integer> m = new HashMap<>();
          Scanner sc = new Scanner(this.in);
          int flag=0;
          while (true) {
            System.out.println("Enter 1 to add stocks to your portfolio");
            System.out.println("Enter q to exit");
            if(flag==1)
              break;
            switch (scan.next()) {
              case "1" -> {
                System.out.println("Enter ticker of stock you want to add to the portfolio");
                String ticker = sc.next();
                System.out.println("Enter quantity of stocks");
                int qty = sc.nextInt();
                m.put(new StocksImpl(ticker), qty);
              }
              case "q" -> flag = 1;
            }
          }
          this.user_model.createPortfolio(m, "p1");
          break;

        case "q":
          return;
      }
    }
  }
}
