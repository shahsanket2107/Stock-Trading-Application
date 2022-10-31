package controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import model.User;
import view.View;

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

  private Map<String, Integer> perform() {

    Map<String, Integer> stocks = new HashMap<>();
    while (true) {
      view.getAddStockMenu();
      Scanner scan = new Scanner(this.in);
      switch (scan.next()) {
        case "1" -> {
          String ticker = view.getTicker();
          if (!user.ifStocksExist(ticker)) {
            while (!user.ifStocksExist(ticker)) {
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
