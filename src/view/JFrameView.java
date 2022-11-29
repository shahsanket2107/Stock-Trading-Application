package view;

import controller.Features;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

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
  private JPanel mainPanel;

  public JFrameView(String name) {

    setTitle("ASSIGNMENT 6");
    setSize(400, 500);
    mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    add(mainPanel);
    setDefaultCloseOperation(EXIT_ON_CLOSE);

    cpBtn = new JButton("Create a flexible portfolio");

    bsBtn = new JButton("Buy stocks");

    ssBtn = new JButton("Sell stocks");

    cbBtn = new JButton("Find cost basis of a portfolio");

    vBtn = new JButton("Find value of a portfolio");

    lpBtn = new JButton("Load portfolio");

    gcBtn = new JButton("Get portfolio composition");

    gpBtn = new JButton("Get portfolio performance");

    ipBtn = new JButton("Invest in a portfolio");

    dcBtn = new JButton("Create portfolio using dollar-cost averaging");
    createMenu(name);
    setVisible(true);
  }

  private void createMenu(String name) {
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(
        BorderFactory.createTitledBorder(
            "Welcome " + name + "!! Choose any option from the menu :"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    JPanel createPortfolioPanel = new JPanel();
    createPortfolioPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(createPortfolioPanel);

    cpBtn.setActionCommand("Create portfolio");
    createPortfolioPanel.add(cpBtn);

    JPanel buyStocksPanel = new JPanel();
    buyStocksPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(buyStocksPanel);

    bsBtn.setActionCommand("Buy Stocks");
    buyStocksPanel.add(bsBtn);

    JPanel sellStocksPanel = new JPanel();
    sellStocksPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(sellStocksPanel);

    ssBtn.setActionCommand("Sell Stocks");
    sellStocksPanel.add(ssBtn);

    JPanel findCostBasisPanel = new JPanel();
    findCostBasisPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(findCostBasisPanel);

    cbBtn.setActionCommand("Cost Basis");
    findCostBasisPanel.add(cbBtn);

   createMenuHelper(dialogBoxesPanel);
  }
  private void createMenuHelper(JPanel dialogBoxesPanel){
    JPanel findValuePanel = new JPanel();
    findValuePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(findValuePanel);

    vBtn.setActionCommand("Value");
    findValuePanel.add(vBtn);

    JPanel loadPortfolioPanel = new JPanel();
    loadPortfolioPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(loadPortfolioPanel);

    lpBtn.setActionCommand("Load Portfolio");
    loadPortfolioPanel.add(lpBtn);

    JPanel getCompositionPanel = new JPanel();
    getCompositionPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(getCompositionPanel);

    gcBtn.setActionCommand("Get portfolio composition");
    getCompositionPanel.add(gcBtn);

    JPanel investPanel = new JPanel();
    investPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(investPanel);

    ipBtn.setActionCommand("Invest in a portfolio");
    investPanel.add(ipBtn);

    JPanel dollarCostPanel = new JPanel();
    dollarCostPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(dollarCostPanel);

    dcBtn.setActionCommand("Create portfolio using dollar-cost averaging");
    dollarCostPanel.add(dcBtn);

    JPanel getPerformancePanel = new JPanel();
    getPerformancePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(getPerformancePanel);
    gpBtn.setActionCommand("Get portfolio performance");
    getPerformancePanel.add(gpBtn);
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
  public ArrayList<String> createPortfolioInput() throws IllegalArgumentException {
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
  public ArrayList<String> getInput() throws IllegalArgumentException {
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
  public ArrayList<String> getInputForPerformance() throws IllegalArgumentException {
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
    if (pName.equals("") || sDate.equals("") || eDate.equals("")) {
      throw new IllegalArgumentException("Input fields cannot be blank");
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(sDate);
    output.add(eDate);
    return output;
  }

  @Override
  public ArrayList<String> getInvestmentDetails() throws IllegalArgumentException {
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    JTextField field4 = new JTextField();
    JTextField field5 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter amount you want to invest", field2,
        "Please enter date (yyyy-MM-dd)", field3,
        "Please enter the commission fee", field4,
        "Please enter the number of stocks you want to invest in", field5
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String amount = "";
    String date = "";
    String commissionFee = "";
    String num = "";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      amount = field2.getText();
      date = field3.getText();
      commissionFee = field4.getText();
      num = field5.getText();
    }
    if (pName.equals("") || amount.equals("") || commissionFee.equals("") || date.equals("")
        || num.equals("")) {
      throw new IllegalArgumentException("Input fields cannot be blank");
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(amount);
    output.add(date);
    output.add(commissionFee);
    output.add(num);
    return output;
  }

  @Override
  public Map<String, Double> getInvestmentShares(int num) throws IllegalArgumentException {
    Map<String, Double> m = new HashMap<>();
    JTextField[] fields = new JTextField[num * 2 + 1];
    JLabel l1;
    JLabel l2;
    JPanel main = new JPanel();
    main.setLayout(new BoxLayout(main, BoxLayout.PAGE_AXIS));
    main.setSize(600, 400);
    main.setBorder(new EmptyBorder(10, 10, 10, 10));
    for (int i = 0; i < num * 2; i += 2) {
      JPanel panel = new JPanel();
      panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
      panel.setVisible(true);
      l1 = new JLabel("Please enter the ticker of stock ");
      l2 = new JLabel("Please enter percentage amount you want to invest");
      fields[i] = new JTextField();
      fields[i + 1] = new JTextField();
      panel.add(l1);
      panel.add(fields[i]);
      panel.add(l2);
      panel.add(fields[i + 1]);
      main.add(panel);
    }

    JScrollPane scrollPane = new JScrollPane(main);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
    scrollPane.setPreferredSize(new Dimension(400, 300));
    add(scrollPane);

    int option = JOptionPane.showConfirmDialog(null, scrollPane, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    if (option == JOptionPane.OK_OPTION) {
      for (int i = 0; i < num * 2 - 1; i += 2) {
        if (fields[i].getText().equals("") || fields[i + 1].getText().equals("")) {
          throw new IllegalArgumentException("Input fields cannot be blank");
        }
        m.put(fields[i].getText(), Double.valueOf(fields[i + 1].getText()));
      }

    }
    return m;
  }

  @Override
  public ArrayList<String> getDollarCostDetails() throws IllegalArgumentException {
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    JTextField field4 = new JTextField();
    JTextField field5 = new JTextField();
    JTextField field6 = new JTextField();
    JTextField field7 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter amount you want to invest", field2,
        "Please enter the commission fee", field3,
        "Please enter start date (yyyy-MM-dd)", field4,
        "Please enter end date (yyyy-MM-dd)", field5,
        "Please enter the interval at which you want to periodically invest", field6,
        "Please enter the number of stocks you want to invest in", field7
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String amount = "";
    String sDate = "";
    String eDate = "";
    String commissionFee = "";
    String interval = "";
    String num = "";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      amount = field2.getText();
      commissionFee = field3.getText();
      sDate = field4.getText();
      eDate = field5.getText();
      interval = field6.getText();
      num = field7.getText();
    }
    if (pName.equals("") || amount.equals("") || commissionFee.equals("") || sDate.equals("")
        || interval.equals("") || num.equals("")) {
      throw new IllegalArgumentException("Input fields cannot be blank");
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(amount);
    output.add(commissionFee);
    output.add(sDate);
    output.add(eDate);
    output.add(interval);
    output.add(num);
    return output;
  }

  @Override
  public void showBlank() {
    JOptionPane.showMessageDialog(null, "Input fields cannot be blank");
  }

  @Override
  public void showChart(Map<String, Double> m, String pName, String sdate, String eDate) {
    JFrame frame = new JFrame();
    frame.setSize(800, 600);

    CategoryDataset dataset = createDataset(m);
    JFreeChart chart = ChartFactory.createStackedBarChart(
        "Performace of Portfolio " + pName + " from " + sdate + " to " + eDate,
        "Dates",
        "Value",
        dataset,
        PlotOrientation.VERTICAL,
        false, true, false
    );
    CategoryAxis axis = chart.getCategoryPlot().getDomainAxis();
    axis.setCategoryLabelPositions(CategoryLabelPositions.UP_90);
    ChartPanel cp = new ChartPanel(chart);
    add(cp);
    setDefaultCloseOperation(EXIT_ON_CLOSE);
    frame.add(cp);
    frame.setVisible(true);
  }

  private CategoryDataset createDataset(Map<String, Double> m) {
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    for (Entry<String, Double> entry : m.entrySet()) {
      dataset.addValue(entry.getValue(), entry.getKey(), entry.getKey());
    }

    return dataset;
  }
}
