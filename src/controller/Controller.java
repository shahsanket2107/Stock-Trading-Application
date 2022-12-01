package controller;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.Stocks;
import model.StocksImpl;
import model.UserModelExtension;
import view.guiView;

/**
 * This controller class has all the methods for features to be implemented in the GUI. This
 * controller acts as a mediator between UserModelExtension model and view. It calls the different
 * methods of the model, passes the results to view and view prints the output.
 */
public class Controller implements Features {

  private final UserModelExtension user;
  private guiView view;

  public Controller(UserModelExtension m, String name) {
    this.user = m;
    user.setName(name);
  }

  public void setView(guiView v) {
    view = v;
    view.addFeatures(this);
  }

  private boolean dateFormatHelper(String date) throws IllegalArgumentException {
    String tempDate = date.replaceAll("[\\s\\-()]", "");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
    if (Integer.parseInt(tempDate) >= Integer.parseInt(curr_date)) {
      throw new IllegalArgumentException(
              "Date cannot be in the future!!");
    }
    if (Integer.parseInt(tempDate) <= 20000101) {
      throw new IllegalArgumentException(
              "Date should be more than 1st January 2000. Try a different date");
    }
    return true;
  }

  private boolean checker(String ticker, int qty, double cf) {
    if (!user.ifStocksExist(ticker)) {
      view.showOutput("Ticker is invalid!");
      return false;
    } else if (qty < 0) {
      view.showOutput("Quantity must be positive!");
      return false;
    } else if (cf < 0) {
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
    ArrayList<String> output;
    try {
      output = view.createPortfolioInput();
      String pName = output.get(0);
      String ticker = output.get(1);
      int qty = 0;
      String date = output.get(3);
      String cFee = output.get(4);
      boolean check = true;
      int chk = user.checkPortfolioExists(pName);
      if (cFee.isEmpty()) {
        cFee = "0";
      }
      double commissionFee = Double.parseDouble(cFee);
      if (chk != 2 && flg != 2) {
        view.showOutput("Portfolio with given name does not exist");
      } else if (chk == 2 && flg == 2) {
        view.showOutput("Portfolio with the given name already exists");
      } else {
        try {
          qty = Integer.parseInt(output.get(2));
        } catch (NumberFormatException e) {
          throw new IllegalArgumentException("Quantity cannot be fractional");
        }
        try {
          check = dateFormatHelper(date);
        } catch (Exception e) {
          view.showOutput(e.toString());
        }
        buySellAndCreateHelper2(pName, ticker, qty, date, check, commissionFee, flg);
      }
    } catch (IllegalArgumentException e) {
      view.showBlank();
    }


  }

  private void buySellAndCreateHelper2(String pName, String ticker, int qty, String date,
                                       boolean check, double commissionFee, int flg) {
    List<Stocks> stocks = new ArrayList<>();
    if (checker(ticker, qty, commissionFee)) {
      ticker = ticker.toUpperCase();
      if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date) && check) {
        String message = "";
        if (flg == 0) {
          try {
            message = user.buyStocks(ticker, qty, pName, date, commissionFee);
            view.showOutput(message);
          } catch (Exception e) {
            view.showOutput(e.toString());
          }
        } else if (flg == 1) {
          try {
            message = user.sellStocks(ticker, qty, pName, date, commissionFee);
            view.showOutput(message);
          } catch (Exception e) {
            view.showOutput(e.toString());
          }
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
    try {
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
    } catch (IllegalArgumentException e) {
      view.showBlank();
    }

  }

  @Override
  public void getCostBasis() {
    try {
      ArrayList<String> output = view.getInput();
      String pName = output.get(0);
      String date = output.get(1);

      int chk = user.checkPortfolioExists(pName);
      if (chk != 2) {
        view.showOutput("Portfolio with given name does not exist");
      } else {
        if (user.isValidFormat(date) && dateFormatHelper(date)) {
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
    } catch (IllegalArgumentException e) {
      view.showBlank();
    }
  }

  @Override
  public void getComposition() {
    try {
      ArrayList<String> output = view.getInput();
      String pName = output.get(0);
      String date = output.get(1);

      int chk = user.checkPortfolioExists(pName);
      if (chk != 2) {
        view.showOutput("Portfolio with given name does not exist");
      } else {
        if (user.isValidFormat(date) && dateFormatHelper(date)) {
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
    } catch (IllegalArgumentException e) {
      view.showBlank();
    }
  }

  @Override
  public void getPerformance() {
    try {
      ArrayList<String> output = view.getInputForPerformance();
      String pName = output.get(0);
      String sDate = output.get(1);
      String eDate = output.get(2);
      int chk = user.checkPortfolioExists(pName);
      if (chk != 2) {
        view.showOutput("Portfolio with given name does not exist");
      } else {
        if (user.isValidFormat(sDate) && dateFormatHelper(sDate) && user.isValidFormat(eDate)
                && dateFormatHelper(eDate)) {
          Map<String, Double> m = new HashMap<>();
          try {
            m = user.showPerformance(pName, sDate, eDate);
            view.showChart(m, pName, sDate, eDate);
          } catch (IllegalArgumentException e) {
            view.showOutput(e.toString());
          }

        } else {
          view.showOutput("Date is not in proper format!!");
        }
      }
    } catch (IllegalArgumentException e) {
      view.showBlank();
    }
  }

  @Override
  public void investInPortfolio() {
    try {
      ArrayList<String> output = view.getInvestmentDetails();
      String pName = output.get(0);
      String amount = output.get(1);
      String date = output.get(2);
      String commissionFee = output.get(3);
      String num = output.get(4);
      Map<String, Double> m = new HashMap<>();
      try {
        int number = Integer.parseInt(num);
        if (number > 0) {
          m = view.getInvestmentShares(number);

          int chk = user.checkPortfolioExists(pName);
          if (chk != 2) {
            view.showOutput("Portfolio with given name does not exist");
          } else {
            try {
              boolean op = user.investFractionalPercentage(pName, date, Double.parseDouble(amount),
                      m,
                      Double.parseDouble(commissionFee));
              if (op) {
                view.showOutput("Amount Invested Successfully!!");
              }
            } catch (Exception e) {
              view.showOutput(e.toString());
            }
          }
        } else {
          view.showOutput("Number of stocks should be greater than 0");
        }
      } catch (NumberFormatException e) {
        view.showOutput("Fractional entry not allowed for number of stocks");
      }

    } catch (IllegalArgumentException e) {
      view.showBlank();
    }

  }

  @Override
  public void createPortfolioUsingDollarCost() {
    try {
      ArrayList<String> output = view.getDollarCostDetails();

      String pName = output.get(0);
      String amount = output.get(1);
      String commissionFee = output.get(2);
      String sDate = output.get(3);
      String eDate = output.get(4);
      String ivl = output.get(5);
      String num = output.get(6);
      Map<String, Double> m = new HashMap<>();
      try {
        int interval = Integer.parseInt(ivl);
        int number = Integer.parseInt(num);

        if (number > 0) {
          m = view.getInvestmentShares(number);
          try {
            boolean op = user.dollarCostAveragingPortfolio(pName, m, Double.parseDouble(amount),
                    Double.parseDouble(commissionFee), sDate, eDate,
                    interval);
            if (op) {
              view.showOutput("Dollar Cost Averaging Portfolio Created Successfully!!");
            }
          } catch (Exception e) {
            view.showOutput(e.toString());
          }
        } else {
          view.showOutput("Number of stocks should be greater than 0");
        }
      } catch (NumberFormatException e) {
        view.showOutput("Fractional entry not allowed for number of stocks and interval");
      }

    } catch (IllegalArgumentException e) {
      view.showBlank();
    }
  }

}
