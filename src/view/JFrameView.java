package view;

import controller.Features;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JFrameView extends JFrame implements IView {

  private final JButton cpBtn;
  private final JButton bsBtn;
  private final JButton ssBtn;
  private final JButton cbBtn;
  private final JButton vBtn;
  private final JButton lpBtn;
  private final JButton gcBtn;
  private final JButton gpBtn;
  private final JButton ipBtn;
  private final JButton dcBtn;

  public JFrameView(String name) {

    setTitle("ASSIGNMENT 6");
    setSize(400, 500);
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //mainScrollPane = new JScrollPane(mainPanel);
    add(mainPanel);

    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(
        BorderFactory.createTitledBorder(
            "Welcome " + name + "!! Choose any option from the menu :"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    JPanel createPortfolioPanel = new JPanel();
    createPortfolioPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(createPortfolioPanel);

    cpBtn = new JButton("Create a flexible portfolio");
    cpBtn.setActionCommand("Create portfolio");
    createPortfolioPanel.add(cpBtn);

    JPanel buyStocksPanel = new JPanel();
    buyStocksPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(buyStocksPanel);

    bsBtn = new JButton("Buy stocks");
    bsBtn.setActionCommand("Buy Stocks");
    buyStocksPanel.add(bsBtn);

    JPanel sellStocksPanel = new JPanel();
    sellStocksPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(sellStocksPanel);

    ssBtn = new JButton("Sell stocks");
    ssBtn.setActionCommand("Sell Stocks");
    sellStocksPanel.add(ssBtn);

    JPanel findCostBasisPanel = new JPanel();
    findCostBasisPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(findCostBasisPanel);

    cbBtn = new JButton("Find cost basis of a portfolio");
    cbBtn.setActionCommand("Cost Basis");
    findCostBasisPanel.add(cbBtn);

    JPanel findValuePanel = new JPanel();
    findValuePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(findValuePanel);

    vBtn = new JButton("Find value of a portfolio");
    vBtn.setActionCommand("Value");
    findValuePanel.add(vBtn);

    JPanel loadPortfolioPanel = new JPanel();
    loadPortfolioPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(loadPortfolioPanel);

    lpBtn = new JButton("Load portfolio");
    lpBtn.setActionCommand("Load Portfolio");
    loadPortfolioPanel.add(lpBtn);

    JPanel getCompositionPanel = new JPanel();
    getCompositionPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(getCompositionPanel);

    gcBtn = new JButton("Get portfolio composition");
    gcBtn.setActionCommand("Get portfolio composition");
    getCompositionPanel.add(gcBtn);

    JPanel getPerformancePanel = new JPanel();
    getPerformancePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(getPerformancePanel);

    gpBtn = new JButton("Get portfolio performance");
    gpBtn.setActionCommand("Get portfolio performance");
    getPerformancePanel.add(gpBtn);

    JPanel investPanel = new JPanel();
    investPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(investPanel);

    ipBtn = new JButton("Invest in a portfolio");
    ipBtn.setActionCommand("Invest in a portfolio");
    investPanel.add(ipBtn);

    JPanel dollarCostPanel = new JPanel();
    dollarCostPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(dollarCostPanel);

    dcBtn = new JButton("Create portfolio using dollar-cost averaging");
    dcBtn.setActionCommand("Create portfolio using dollar-cost averaging");
    dollarCostPanel.add(dcBtn);

    setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    cpBtn.addActionListener(evt -> features.createPortfolio());
    bsBtn.addActionListener(evt -> features.buyStocks());
    lpBtn.addActionListener(evt -> features.loadPortfolio());
    ssBtn.addActionListener(evt -> features.sellStocks());
    vBtn.addActionListener(evt -> features.getValuation());
    cbBtn.addActionListener(evt -> features.getCostBasis());
    gcBtn.addActionListener(evt -> features.getComposition());
    gpBtn.addActionListener(evt -> features.getPerformance());
    ipBtn.addActionListener(evt -> features.investInPortfolio());
    dcBtn.addActionListener(evt -> features.createPortfolioUsingDollarCost());
  }

  @Override
  public void showOutput(String message) {
    JOptionPane.showMessageDialog(null, message);
  }

  @Override
  public ArrayList<String> createPortfolioInput() throws IllegalArgumentException{
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    JTextField field4 = new JTextField();
    JTextField field5 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter ticker of stock you want to add to portfolio", field2,
        "Please enter quantity of stocks", field3,
        "Please enter date in format (yyyy-MM-dd)", field4,
        "Please enter the commission fee", field5
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String ticker = "";
    String qty = "";
    String date = "";
    String commissionFee = "";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      ticker = field2.getText();
      qty = field3.getText();
      date = field4.getText();
      commissionFee = field5.getText();
    }
    if (pName.equals("") || ticker.equals("") || date.equals("") || commissionFee.equals("")
        || qty.equals("")) {
      throw new IllegalArgumentException("Input fields cannot be bank");
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(ticker);
    output.add(qty);
    output.add(date);
    output.add(commissionFee);
    return output;
  }

  @Override
  public ArrayList<String> getInput() throws IllegalArgumentException{
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter date in format (yyyy-MM-dd)", field2,
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String date = "";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      date = field2.getText();
    }
    if (pName.equals("") || date.equals("")) {
      throw new IllegalArgumentException("Input cannot be blank");
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(date);
    return output;
  }

  @Override
  public ArrayList<String> getInputForPerformance() throws IllegalArgumentException{
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter start date in format (yyyy-MM-dd)", field2,
        "Please enter end date in format (yyyy-MM-dd)", field3
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String sDate = "";
    String eDate = "";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      sDate = field2.getText();
      eDate = field3.getText();
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(sDate);
    output.add(eDate);
    return output;
  }

  @Override
  public ArrayList<String> getInvestmentDetails() throws IllegalArgumentException{
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    JTextField field4 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter amount you want to invest", field2,
        "Please enter date (yyyy-MM-dd)", field3,
        "Please enter the commission fee", field4
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String amount = "";
    String date = "";
    String commissionFee = "";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      amount = field2.getText();
      date = field3.getText();
      commissionFee = field4.getText();
    }
    if (pName.equals("") || amount.equals("") || commissionFee.equals("") || date.equals("")) {
      throw new IllegalArgumentException("Input fields cannot be blank");
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(amount);
    output.add(date);
    output.add(commissionFee);
    return output;
  }

  @Override
  public Map<String, Double> getInvestmentShares() throws IllegalArgumentException {
    Map<String, Double> m = new HashMap<>();
    int num = Integer.parseInt(
        JOptionPane.showInputDialog("Please enter the number of stocks you want to invest in:"));
    for (int i = 0; i < num; i++) {
      JTextField field1 = new JTextField();
      JTextField field2 = new JTextField();
      Object[] message = {
          "Please enter the ticker of stock " + (i + 1) + "", field1,
          "Please enter percentage amount you want to invest", field2,
      };
      int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
          JOptionPane.OK_CANCEL_OPTION);
      if (option == JOptionPane.OK_OPTION) {
        if (field1.getText().equals("") || field2.getText().equals("")) {
          throw new IllegalArgumentException("Input fields cannot be blank");
        }
        m.put(field1.getText(), Double.valueOf(field2.getText()));
      }

    }
    return m;
  }

  @Override
  public ArrayList<String> getDollarCostDetails() throws IllegalArgumentException{
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    JTextField field4 = new JTextField();
    JTextField field5 = new JTextField();
    JTextField field6 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter amount you want to invest", field2,
        "Please enter the commission fee", field3,
        "Please enter start date (yyyy-MM-dd)", field4,
        "Please enter end date (yyyy-MM-dd)", field5,
        "Please enter the interval at which you want to periodically invest", field6
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String amount = "";
    String sDate = "";
    String eDate = "";
    String commissionFee = "";
    String interval = "";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      amount = field2.getText();
      commissionFee = field3.getText();
      sDate = field4.getText();
      eDate = field5.getText();
      interval = field6.getText();
    }
    if (pName.equals("") || amount.equals("") || commissionFee.equals("") || sDate.equals("")
        || interval.equals("")) {
      throw new IllegalArgumentException("Input fields cannot be blank");
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(amount);
    output.add(commissionFee);
    output.add(sDate);
    output.add(eDate);
    output.add(interval);
    return output;
  }

  @Override
  public void showBlank() {
    JOptionPane.showMessageDialog(null, "Input fields cannot be blank");
  }
}
