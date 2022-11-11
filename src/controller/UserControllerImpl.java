package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import model.Stocks;
import model.StocksImpl;
import model.User;
import view.ViewImpl;

/**
 * This is the controller class implementation which implements the UserController. This controller
 * acts as a mediator between user model and view. It calls the different methods of user model,
 * passes the results to view and view prints the output.
 */
public class UserControllerImpl implements UserController {

  private final InputStream in;
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
    this.user = user;
    this.view = view;
    view.setStream(out);
  }

  /**
   * This is a helper method for creating portfolio which takes the ticker of stock and the quantity
   * which the user wants to add to his portfolio. He can add as many stocks as he wants, and he can
   * quit adding by entering q.
   *
   * @return A map of Stock's sticker symbol mapped to its quantity.
   */

  private Map<String, Integer> perform(Scanner sc) {

    Map<String, Integer> stocks = new HashMap<>();
    while (true) {
      view.getAddStockMenu();
      switch (sc.nextLine()) {
        case "1":
          view.getTicker();
          String ticker = sc.nextLine();
          if (!user.ifStocksExist(ticker)) {
            while (!user.ifStocksExist(ticker)) {
              view.invalidTicker();
              view.getTicker();
              ticker = sc.nextLine();
            }
          }
          ticker = ticker.toUpperCase();
          int qty = 0;

          do {
            view.getQty();
            String s = sc.nextLine();
            try {
              qty = Integer.parseInt(s);
              if (qty <= 0) {
                view.qtyPositive();
              }
            } catch (NumberFormatException e) {
              view.qtyInteger();
            }
          }
          while (qty <= 0);
          if (!stocks.containsKey(ticker)) {
            stocks.put(ticker, qty);
          } else {
            int temp = stocks.get(ticker);
            stocks.put(ticker, qty + temp);
          }
          break;
        case "q":
          return stocks;
        default:
          view.seeDefault();
      }
    }
  }

  private String getTicker(Scanner sc) {
    String ticker = sc.nextLine();
    if (!user.ifStocksExist(ticker)) {
      while (!user.ifStocksExist(ticker)) {
        view.invalidTicker();
        view.getTicker();
        ticker = sc.nextLine();
      }
    }
    ticker = ticker.toUpperCase();
    return ticker;
  }

  private int getQty(Scanner sc) {
    int qty = 0;
    do {
      view.getQty();
      String s = sc.nextLine();
      try {
        qty = Integer.parseInt(s);
        if (qty <= 0) {
          view.qtyPositive();
        }
      } catch (NumberFormatException e) {
        view.qtyInteger();
      }
    }
    while (qty <= 0);
    return qty;
  }

  private List<Stocks> performJson(Scanner sc) {
    List<Stocks> stocks = new ArrayList<>();
    while (true) {
      view.getAddStockMenu();
      switch (sc.nextLine()) {
        case "1":
          view.getTicker();
          String ticker = getTicker(sc);
          int qty = getQty(sc);
          view.getDate();
          String date = sc.nextLine();
          if (user.isValidFormat(date)) {
            Stocks s = new StocksImpl(date, ticker, qty);
            stocks.add(s);
          } else {
            view.invalidDate();
          }
          break;
        case "q":
          return stocks;
        default:
          view.seeDefault();
      }
    }
  }

  /**
   * This helper method takes portfolio name as input from the user.
   *
   * @return the portfolio name.
   */
  private String portfolioName(Scanner sc) {
    String portfolioName;
    do {
      view.getPortfolioName();
      portfolioName = sc.nextLine();
      if (portfolioName.equals("")) {
        view.emptyPortfolioMessage();
      }
    }
    while (portfolioName.equals(""));
    return portfolioName;
  }

  private void createPortfolio(Scanner scan) {
    String portfolioName = portfolioName(scan);
    while (user.checkPortfolioExists(portfolioName)) {
      view.alreadyExists();
      portfolioName = portfolioName(scan);
    }
    try {
      Map<String, Integer> m = perform(scan);
      user.createPortfolio(portfolioName, m);
      view.getPortfolioMessage();
    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
    }
  }

  private void createFlexiblePortfolio(Scanner scan) {
    String portfolioName = portfolioName(scan);
    while (user.checkPortfolioExists(portfolioName)) {
      view.alreadyExists();
      portfolioName = portfolioName(scan);
    }
    try {
      List<Stocks> jsonArray = performJson(scan);
      user.createFlexiblePortfolio(portfolioName, jsonArray);
      view.getPortfolioMessage();

    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
    }
  }

  private void getPortfolioValuation(Scanner scan) {
    String pName = portfolioName(scan);
    view.getDate();
    String date = scan.nextLine();
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
  }

  private void getPortfolioComposition(Scanner scan) {
    String p_name = portfolioName(scan);
    StringBuilder composition = user.getPortfolioComposition(p_name);
    view.displayMessage(String.valueOf(composition));
  }

  private void getFlexiblePortfolioComposition(Scanner scan) {
    String p_name = portfolioName(scan);
    StringBuilder composition = user.getFlexiblePortfolioComposition(p_name);
    view.displayMessage(String.valueOf(composition));
  }

  private void loadPortfolio(Scanner scan) {
    view.getFileName();
    String pfName = scan.nextLine();
    try {
      String output = user.loadPortfolio(pfName);
      view.displayMessage(output);
    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
    }
  }

  private void loadFlexiblePortfolio(Scanner scan) {
    view.getFileName();
    String fName = scan.nextLine();
    try {
      String output = user.loadFlexiblePortfolio(fName);
      view.displayMessage(output);
    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
    }
  }

  private void sellStocks(Scanner scan) {
    view.getPortfolioName();
    String pName = scan.nextLine();
    while (!user.checkPortfolioExists(pName)) {
      view.portfolioNotExist();
      pName = portfolioName(scan);
    }
    view.getTicker();
    String ticker = getTicker(scan);
    int qty = getQty(scan);
    view.getDate();
    String date = scan.nextLine();
    if (user.isValidFormat(date)) {
      String message = user.sellStocks(ticker, qty, pName, date);
      view.getSellStockMessage();
    } else {
      view.invalidDate();
    }
  }

  private void buyStocks(Scanner scan) {
    view.getPortfolioName();
    String pName = scan.nextLine();
    while (!user.checkPortfolioExists(pName)) {
      view.portfolioNotExist();
      pName = portfolioName(scan);
    }
    view.getTicker();
    String ticker = getTicker(scan);
    int qty = getQty(scan);
    view.getDate();
    String date = scan.nextLine();
    if (user.isValidFormat(date)) {
      user.buyStocks(ticker, qty, pName, date);
      view.getBuyStockMessage();
    } else {
      view.invalidDate();
    }

  }

  @Override
  public void runGo() {
    Scanner scan = new Scanner(this.in);
    view.getName();
    String name = scan.nextLine();
    user.setName(name);
    view.displayName(user.getName());
    while (true) {
      view.getMenu();
      switch (scan.nextLine()) {
        case "1":
          createPortfolio(scan);
          break;
        case "2":
          getPortfolioComposition(scan);
          break;
        case "3":
          getPortfolioValuation(scan);
          break;
        case "4":
          StringBuilder result = user.getPortfoliosName();
          view.displayMessage(String.valueOf(result));
          break;
        case "5":
          loadPortfolio(scan);
          break;
        case "6":
          createFlexiblePortfolio(scan);
          break;
        case "7":
          getFlexiblePortfolioComposition(scan);
          break;
        case "8":
          loadFlexiblePortfolio(scan);
          break;
        case "9":
          buyStocks(scan);
          break;
        case "10":
          sellStocks(scan);
          break;
        case "q":
          return;
        default:
          view.seeDefault();
      }
    }
  }


}
