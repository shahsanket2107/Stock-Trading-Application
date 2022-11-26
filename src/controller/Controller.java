package controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import model.Stocks;
import model.StocksImpl;
import model.UserModelExtension;
import view.IView;

public class Controller implements Features {

  private UserModelExtension user;
  private IView view;

  public Controller(UserModelExtension m, String name) {
    this.user = m;
    user.setName(name);
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

  private boolean checker(String ticker, int qty,double cf) {
    if (!user.ifStocksExist(ticker)) {
      view.showOutput("Ticker is invalid!");
      return false;
    } else if (qty < 0) {
      view.showOutput("Quantity must be positive!");
      return false;
    } else if (cf<0) {
      view.showOutput("Commission fee cannot be negative");
      return false;
    }
    return true;
  }

  @Override
  public void createPortfolio() {
    try {
      buySellAndCreateHelper(2);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  @Override
  public void buyStocks() {
    try {
      buySellAndCreateHelper(0);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  @Override
  public void loadPortfolio() {
    final JFileChooser fchooser = new JFileChooser(".");
    FileNameExtensionFilter filter = new FileNameExtensionFilter(
        "JSON files only", "json");
    fchooser.setFileFilter(filter);
    int retvalue = fchooser.showOpenDialog(null);
    if (retvalue == JFileChooser.APPROVE_OPTION) {
      File f = fchooser.getSelectedFile();
      try {
        String output = user.loadFlexiblePortfolio(String.valueOf(f));
        view.showOutput(output);
      } catch (IllegalArgumentException e) {
        view.showOutput(e.getMessage());
      }
    }
  }

  private void buySellAndCreateHelper(int flg) throws IllegalArgumentException {
    List<Stocks> stocks = new ArrayList<>();
    ArrayList<String> output = view.createPortfolioInput();
    String pName = output.get(0);
    String ticker = output.get(1);
    int qty = 0;
    try {
      qty = Integer.parseInt(output.get(2));
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Quantity cannot be fractional");

    }
    String date = output.get(3);
    double commissionFee = Double.parseDouble(output.get(4));
    boolean check = true;
    try {
      check = dateFormatHelper(date);
    } catch (Exception e) {
      JOptionPane.showMessageDialog(null, e.toString());
    }
    int chk = user.checkPortfolioExists(pName);
    if (chk != 2 && flg != 2) {
      view.showOutput("Portfolio with given name does not exist");
    } else if (chk == 2 && flg == 2) {
      view.showOutput("Portfolio with the given name already exists");
    } else if (checker(ticker, qty,commissionFee)) {
      ticker = ticker.toUpperCase();
      if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date) && check) {
        String message = "";
        if (flg == 0) {
          message = user.buyStocks(ticker, qty, pName, date, commissionFee);
          view.showOutput(message);
        } else if (flg == 1) {
          message = user.sellStocks(ticker, qty, pName, date, commissionFee);
          view.showOutput(message);
        } else if (flg == 2) {
          Stocks s = new StocksImpl(date, ticker, qty);
          stocks.add(s);
          try {
            user.createFlexiblePortfolio(pName, stocks, commissionFee);
            view.showOutput("Portfolio created successfully!");
          } catch (Exception e) {
            view.showOutput(e.toString());
          }
        }

      } else if (!user.isValidFormat(date)) {
        view.showOutput("Date is not in proper format!!");
      } else if (!user.validateDateAccToApi(ticker, date)) {
        view.showOutput(
            "Stock market is closed at this date, so please enter a different date!!");
      }
    }
  }

  @Override
  public void sellStocks() {
    try {
      buySellAndCreateHelper(1);
    } catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(null, e.getMessage());
    }
  }

  @Override
  public void getValuation() {
    ArrayList<String> output = view.getInput();
    String pName = output.get(0);
    String date = output.get(1);
    int chk = user.checkPortfolioExists(pName);
    if (chk != 2) {
      view.showOutput("Portfolio with given name does not exist");
    } else {
      if (user.isValidFormat(date)) {
        StringBuilder result = new StringBuilder();
        try {
          result.append(user.getFlexiblePortfolioTotalValuation(date, pName));
        } catch (IllegalArgumentException e) {
          result.append(e.getMessage());
        }
        view.showOutput(String.valueOf(result));
      } else {
        view.showOutput("Date is not in proper format!!");
      }
    }
  }

  @Override
  public void getCostBasis() {
    ArrayList<String> output = view.getInput();
    String pName = output.get(0);
    String date = output.get(1);
    int chk = user.checkPortfolioExists(pName);
    if (chk != 2) {
      view.showOutput("Portfolio with given name does not exist");
    } else {
      if (user.isValidFormat(date)) {
        StringBuilder result = new StringBuilder();
        try {
          result = user.getCostBasis(date, pName);
        } catch (IllegalArgumentException e) {
          result.append(e.getMessage());
        }
        view.showOutput(String.valueOf(result));
      } else {
        view.showOutput("Date is not in proper format!!");
      }
    }
  }

  @Override
  public void getComposition() {
    ArrayList<String> output = view.getInput();
    String pName = output.get(0);
    String date = output.get(1);
    int chk = user.checkPortfolioExists(pName);
    if (chk != 2) {
      view.showOutput("Portfolio with given name does not exist");
    } else {
      if (user.isValidFormat(date)) {
        StringBuilder result = new StringBuilder();
        try {
          result = user.getFlexiblePortfolioComposition(pName, date);
        } catch (IllegalArgumentException e) {
          result.append(e.getMessage());
        }
        view.showOutput(String.valueOf(result));
      } else {
        view.showOutput("Date is not in proper format!!");
      }
    }
  }

  @Override
  public void getPerformance() {
    ArrayList<String> output = view.getInput();
    String pName = output.get(0);
    String sDate = output.get(1);
    String eDate = output.get(2);
    int chk = user.checkPortfolioExists(pName);
    if (chk != 2) {
      view.showOutput("Portfolio with given name does not exist");
    }
  }

  @Override
  public void investInPortfolio() {

  }

  @Override
  public void createPortfolioUsingDollarCost() {

  }
}
