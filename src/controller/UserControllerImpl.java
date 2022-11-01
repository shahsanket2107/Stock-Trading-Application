package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.User;
import view.ViewImpl;

public class UserControllerImpl implements UserController {

  private final InputStream in;
  private final PrintStream out;
  private final User user;
  private final ViewImpl view;

  /**
   * This is the constructor for UserControllerImpl which initializes user model and view along with
   * Readable in and appendable out for reading and writing.
   *
   * @param in   Used for reading input.
   * @param out  Used for writing output.
   * @param user Object of User model.
   * @param view Object of view class.
   */
  public UserControllerImpl(InputStream in, PrintStream out, User user, ViewImpl view) {
    this.in = in;
    this.out = out;
    this.user = user;
    this.view = view;
    view.setStream(this.out);
  }

  /**
   * This is a helper method for creating portfolio which takes the ticker of stock and the quantity
   * which the user wants to add to his portfolio. He can add as many stocks as he wants and he can
   * quit adding by entering q.
   *
   * @return A map of Stock's sticker symbol mapped to its quantity.
   */

  private Map<String, Integer> perform() {

    Map<String, Integer> stocks = new HashMap<>();
    while (true) {
      view.getAddStockMenu();
      Scanner sc = new Scanner(this.in);
      switch (sc.next()) {
        case "1" -> {
          view.getTicker();
          Scanner sc1 = new Scanner(this.in);
          String ticker = sc1.nextLine();
          if (!user.ifStocksExist(ticker)) {
            while (!user.ifStocksExist(ticker)) {
              view.invalidTicker();
              view.getTicker();
              ticker = sc1.nextLine();
            }
          }
          ticker = ticker.toUpperCase();
          int qty = 0;

          do {
            view.getQty();
            String s = sc1.nextLine();
            try {
              qty = Integer.parseInt(s);
              if (qty <= 0) {
                view.qtyPositive();
              }
            } catch (NumberFormatException e) {
              view.qtyInteger();
            }
          } while (qty <= 0);
          if (!stocks.containsKey(ticker)) {
            stocks.put(ticker, qty);
          } else {
            int temp = stocks.get(ticker);
            stocks.put(ticker, qty + temp);
          }

        }
        case "q" -> {
          return stocks;
        }
        default -> view.seeDefault();
      }
    }
  }

  /**
   * This helper method takes portfolio name as input from the user.
   * @return the portfolio name.
   */
  private String portfolioName() {
    String portfolioName;
    do {
      Scanner sc = new Scanner(this.in);
      view.getPortfolioName();
      portfolioName = sc.nextLine();
      if (portfolioName.equals("")) {
        view.emptyPortfolioMessage();
      }
    } while (portfolioName.equals(""));
    return portfolioName;
  }


  @Override
  public void go() {
    Scanner scan = new Scanner(this.in);
    view.getName();
    String name = scan.nextLine();
    user.setName(name);
    view.displayName(user.getName());
    while (true) {
      view.getMenu();
      switch (scan.next()) {
        case "1":
          String portfolioName = portfolioName();

          while (user.checkPortfolioExists(portfolioName)) {
            view.alreadyExists();
            portfolioName = portfolioName();
          }
          try {
            user.createPortfolio(portfolioName, perform());
          } catch (IllegalArgumentException e) {
            view.displayMessage(e.getMessage());
          }
          break;
        case "2":
          String p_name = portfolioName();
          StringBuilder composition = user.getPortfolioComposition(p_name);
          view.displayMessage(String.valueOf(composition));
          break;
        case "3":
          String pName = portfolioName();
          view.getDate();
          Scanner sc3 = new Scanner(this.in);
          String date = sc3.nextLine();
          if (user.isValidFormat(date)) {
            StringBuilder result = new StringBuilder();
            try {
              result.append(user.getTotalValuation(date, pName));
            } catch (IllegalArgumentException e) {
              result.append(e.getMessage());
            }
            view.displayMessage(String.valueOf(result));
          } else {
            view.invalidDate();
          }
          break;
        case "4":
          StringBuilder result = user.getPortfoliosName();
          view.displayMessage(String.valueOf(result));
          break;
        case "5":
          view.getFileName();
          Scanner sc5 = new Scanner(this.in);
          String pfName = sc5.nextLine();
          try {
            String output = user.loadPortfolio(pfName);
            view.displayMessage(output);
          } catch (IllegalArgumentException e) {
            view.displayMessage(e.getMessage());
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
