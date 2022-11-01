package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.User;
import view.ViewImpl;

public class UserControllerImpl implements UserController {

  private final Readable in;
  private final Appendable out;
  private final User user;
  private final ViewImpl view;

  /**
   * This is the constructor for UserControllerImpl which initializes user model and view along with
   * Readable in and appendable out for reading and writing.
   *
   * @param in   Used for reading input.
   * @param out  Used for writing output.
   * @param user oObject of User model.
   * @param view Object of view class.
   */
  public UserControllerImpl(Readable in, Appendable out, User user, ViewImpl view) {
    this.in = in;
    this.out = out;
    this.user = user;
    this.view = view;
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
      Scanner scan = new Scanner(this.in);
      switch (scan.next()) {
        case "1" -> {
          String ticker = view.getTicker();
          if (user.ifStocksExist(ticker)) {
            while (user.ifStocksExist(ticker)) {
              view.invalidTicker();
              ticker = view.getTicker();
            }
          }
          ticker = ticker.toUpperCase();
          int qty = view.getQty();
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


  @Override
  public void go() {
    Scanner scan = new Scanner(this.in);
    String name = view.getName();
    user.setName(name);
    view.displayName(user.getName());
    while (true) {
      view.getMenu();
      switch (scan.next()) {
        case "1":
          String portfolioName = view.getPortfolioName();
          while (user.checkPortfolioExists(portfolioName)) {
            view.alreadyExists();
            portfolioName = view.getPortfolioName();
          }
          try {
            user.createPortfolio(portfolioName, perform());
          } catch (IllegalArgumentException e) {
            view.displayExceptions(e.getMessage());
          }
          break;
        case "2":
          String p_name = view.getPortfolioName();
          StringBuilder composition = user.getPortfolioComposition(p_name);
          view.viewComposition(composition);
          break;
        case "3":
          String pName = view.getPortfolioName();
          String date = view.getDate();
          if (user.isValidFormat(date)) {
            StringBuilder result = new StringBuilder();
            try {
              result.append(user.getTotalValuation(date, pName));
            } catch (IllegalArgumentException e) {
              result.append(e.getMessage());
            }
            view.displayResult(result);
          } else {
            view.invalidDate();
          }
          break;
        case "4":
          StringBuilder result = user.getPortfoliosName();
          view.displayResult(result);
          break;
        case "5":
          String pfName = view.getFileName();
          try {
            String output = user.loadPortfolio(pfName);
            view.getLoadPortfolio(output);
          } catch (IllegalArgumentException e) {
            view.displayExceptions(e.getMessage());
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
