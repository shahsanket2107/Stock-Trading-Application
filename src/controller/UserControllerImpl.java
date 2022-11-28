package controller;

import java.io.InputStream;
import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
          boolean check;
          try {
            check = dateFormatHelper(date);
          } catch (IllegalArgumentException e) {
            check = false;
            view.displayMessage(e.getMessage());
          }
          if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date) && check) {
            Stocks s = new StocksImpl(date, ticker, qty);
            stocks.add(s);
          } else if (!user.isValidFormat(date) && check) {
            view.invalidDate();
          } else if (check && !user.validateDateAccToApi(ticker, date)) {
            view.dataNotFound();
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
    int check = user.checkPortfolioExists(portfolioName);
    while (check == 1 || check == 2) {
      view.alreadyExists();
      portfolioName = portfolioName(scan);
      check = user.checkPortfolioExists(portfolioName);
    }
    try {
      Map<String, Integer> m = perform(scan);
      user.createPortfolio(portfolioName, m);
      view.getPortfolioMessage();
    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
    }
  }

  private double checkCommissionFee(Scanner scan) {
    view.getCommissionFee();
    double commissionFee = Double.parseDouble(scan.nextLine());
    if (commissionFee <= 0) {
      return -1;
    }
    return commissionFee;
  }

  private void createFlexiblePortfolio(Scanner scan) {
    String portfolioName = portfolioName(scan);
    int check = user.checkPortfolioExists(portfolioName);
    while (check == 2 || check == 1) {
      view.alreadyExists();
      portfolioName = portfolioName(scan);
      check = user.checkPortfolioExists(portfolioName);
    }

    try {
      List<Stocks> jsonArray = performJson(scan);
      double commissionFee = checkCommissionFee(scan);
      if (commissionFee != -1) {
        user.createFlexiblePortfolio(portfolioName, jsonArray, commissionFee);
        view.getPortfolioMessage();
      } else {
        view.invalidCommissionFee();
      }

    } catch (IllegalArgumentException e) {
      view.displayMessage(e.getMessage());
    }
  }

  private boolean dateFormatHelper(String date) throws IllegalArgumentException {
    String temp_date = date.replaceAll("[\\s\\-()]", "");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
    if (Integer.parseInt(temp_date) >= Integer.parseInt(curr_date)) {
      throw new IllegalArgumentException(
              "Date cannot be greater or equal to current date. Try a different date");
    }
    if (Integer.parseInt(temp_date) <= 20000101) {
      throw new IllegalArgumentException(
              "Date should be more than 1st January 2000. Try a different date");
    }
    return true;
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
    view.getDate();
    String date = scan.nextLine();
    if (user.isValidFormat(date)) {
      try {
        StringBuilder composition = user.getFlexiblePortfolioComposition(p_name, date);
        view.displayMessage(String.valueOf(composition));
      } catch (IllegalArgumentException e) {
        view.displayMessage(e.getMessage());
      }
    } else {
      view.invalidDate();
    }
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
    String pName = portfolioName(scan);
    if (user.checkPortfolioExists(pName) != 2) {
      view.portfolioNotExist();
    } else {
      view.sellStock();
      String ticker = getTicker(scan);
      int qty = getQty(scan);
      view.getDate();
      String date = scan.nextLine();
      double commissionFee = checkCommissionFee(scan);
      if (commissionFee != -1) {
        if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date)) {
          try {
            String message = user.sellStocks(ticker, qty, pName, date, commissionFee);
            view.displayMessage(message);
          } catch (IllegalArgumentException e) {
            view.displayMessage(e.getMessage());
          }
        } else if (!user.isValidFormat(date)) {
          view.invalidDate();
        } else {
          view.dataNotFound();
        }
      } else {
        view.invalidCommissionFee();
      }
    }
  }


  private void buyStocks(Scanner scan) {
    String pName = portfolioName(scan);
    if (user.checkPortfolioExists(pName) != 2) {
      view.portfolioNotExist();
    } else {
      view.getTicker();
      String ticker = getTicker(scan);
      int qty = getQty(scan);
      view.getDate();
      String date = scan.nextLine();
      double commissionFee = checkCommissionFee(scan);
      if (commissionFee != -1) {
        if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date)) {
          try {
            String message = user.buyStocks(ticker, qty, pName, date, commissionFee);
            view.displayMessage(message);
          } catch (IllegalArgumentException e) {
            view.displayMessage(e.getMessage());
          }
        } else if (!user.isValidFormat(date)) {
          view.invalidDate();
        } else {
          view.dataNotFound();
        }
      } else {
        view.invalidCommissionFee();
      }
    }
  }

  private void getFlexiblePortfolioValuation(Scanner scan) {
    String pName = portfolioName(scan);
    if (user.checkPortfolioExists(pName) != 2) {
      view.portfolioNotExist();
    } else {
      view.getDate();
      String date = scan.nextLine();
      if (user.isValidFormat(date)) {
        StringBuilder result = new StringBuilder();
        try {
          result.append(user.getFlexiblePortfolioTotalValuation(date, pName));
        } catch (IllegalArgumentException e) {
          result.append(e.getMessage());
        }
        view.displayMessage(String.valueOf(result));
      } else {
        view.invalidDate();
      }
    }
  }

  private void getCostBasis(Scanner scan) {
    String pName = portfolioName(scan);
    if (user.checkPortfolioExists(pName) != 2) {
      view.portfolioNotExist();
    } else {
      view.getDate();
      String date = scan.nextLine();
      if (user.isValidFormat(date)) {
        StringBuilder result;
        try {
          result = user.getCostBasis(date, pName);
          view.displayMessage(String.valueOf(result));
        } catch (IllegalArgumentException e) {
          view.displayMessage(e.getMessage());
        }
      } else {
        view.invalidDate();
      }
    }
  }

  private void displayChart(Scanner scan) {
    String pName = portfolioName(scan);
    if (user.checkPortfolioExists(pName) != 2) {
      view.portfolioNotExist();
    } else {
      view.getStartDate();
      String sDate = scan.nextLine();
      view.getEndDate();
      String eDate = scan.nextLine();
      if (user.isValidFormat(sDate) && user.isValidFormat(eDate)) {
        try {
          StringBuilder result = user.displayChart(sDate, eDate, pName);
          view.displayMessage(String.valueOf(result));
        } catch (IllegalArgumentException ex) {
          view.displayMessage(ex.getMessage());
        }
      } else {
        view.invalidDate();
      }
    }
  }

  private void getPortfoliosNames() {
    StringBuilder result = user.getPortfoliosName();
    view.displayMessage(String.valueOf(result));
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
          getPortfoliosNames();
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
        case "11":
          getFlexiblePortfolioValuation(scan);
          break;
        case "12":
          getCostBasis(scan);
          break;
        case "13":
          displayChart(scan);
          break;
        case "q":
          return;
        default:
          view.seeDefault();
      }
    }
  }
}
