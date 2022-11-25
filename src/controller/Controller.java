package controller;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import model.Stocks;
import model.StocksImpl;
import model.User;
import view.IView;

public class Controller implements Features {

  private User user;
  private IView view;

  public Controller(User m) {
    user = m;
  }

  public void setView(IView v) {
    view = v;
    view.addFeatures(this);
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

  private boolean checker(String pName, String ticker, int qty) {
    int chk = user.checkPortfolioExists(pName);
    if (chk == 2) {
      view.showOutput("Portfolio with given name already exists");
      return false;
    } else if (!user.ifStocksExist(ticker)) {
      view.showOutput("Ticker is invalid!");
      return false;
    } else if (qty < 0) {
      view.showOutput("Quantity must be positive!");
      return false;
    }
    return true;
  }

  @Override
  public void createPortfolio() {
    user.setName("Strange");
    List<Stocks> stocks = new ArrayList<>();
    ArrayList<String> output = view.createPortfolioInput();
    String pName = output.get(0);
    String ticker = output.get(1);
    int qty = Integer.parseInt(output.get(2));
    String date = output.get(3);
    boolean check = true;
    try {
      check = dateFormatHelper(date);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.toString());
    }
    if(checker(pName, ticker, qty)) {
      ticker = ticker.toUpperCase();
      if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date) && check) {
        Stocks s = new StocksImpl(date, ticker, qty);
        stocks.add(s);
      } else if (!user.isValidFormat(date)) {
        view.showOutput("Date is not in proper format!!");
      } else if (!user.validateDateAccToApi(ticker, date)) {
        view.showOutput(
            "Stock market is closed at this date, so please enter a different date!!");
      }
      try {
        user.createFlexiblePortfolio(pName, stocks);
        view.showOutput("Portfolio created successfully!");
      } catch (Exception e) {
        view.showOutput(e.toString());
      }
    }


  }

  @Override
  public void buyStocks() {
    user.setName("Strange");
    ArrayList<String> output = view.createPortfolioInput();
    String pName = output.get(0);
    String ticker = output.get(1);
    int qty = Integer.parseInt(output.get(2));
    String date = output.get(3);
    boolean check = true;
    try {
      check = dateFormatHelper(date);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.toString());
    }
    if(checker(pName, ticker, qty))  {
      ticker = ticker.toUpperCase();
      if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date) && check) {
        String message = user.buyStocks(ticker, qty, pName, date);
        view.showOutput(message);
      } else if (!user.isValidFormat(date)) {
        view.showOutput("Date is not in proper format!!");
      } else if (!user.validateDateAccToApi(ticker, date)) {
        view.showOutput(
            "Stock market is closed at this date, so please enter a different date!!");
      }

    }
  }
}
