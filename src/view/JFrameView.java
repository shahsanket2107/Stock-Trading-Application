package view;

import controller.Features;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
  private final JPanel mainPanel;

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

  private void createMenuHelper(JPanel dialogBoxesPanel) {
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

  private ArrayList<String> inputFieldHelper(Object[] message, List<JTextField> fields)
          throws IllegalArgumentException {
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
            JOptionPane.OK_CANCEL_OPTION);
    int n = fields.size();
    String[] inputs = new String[n];
    for (int i = 0; i < n; i++) {
      inputs[i] = "";
    }

    for (int i = 0; i < n; i++) {
      if (option == JOptionPane.OK_OPTION) {
        inputs[i] = fields.get(i).getText();
      }
    }

    for (int i = 0; i < n; i++) {
      if (inputs[i].equals("")) {
        throw new IllegalArgumentException("Input fields cannot be bank");
      }
    }

    ArrayList<String> output = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      output.add(inputs[i]);
    }
    return output;
  }

  @Override
  public ArrayList<String> createPortfolioInput() throws IllegalArgumentException {
    List<JTextField> fields = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      fields.add(new JTextField());
    }
    Object[] message = {
            "Please enter your portfolio name", fields.get(0),
            "Please enter ticker of stock you want to add to portfolio", fields.get(1),
            "Please enter quantity of stocks", fields.get(2),
            "Please enter date in format (yyyy-MM-dd)", fields.get(3),
            "Please enter the commission fee", fields.get(4)
    };
    ArrayList<String> result;
    result = inputFieldHelper(message, fields);
    return result;
  }

  @Override
  public ArrayList<String> getInput() throws IllegalArgumentException {
    List<JTextField> fields = new ArrayList<>();
    for (int i = 0; i < 2; i++) {
      fields.add(new JTextField());
    }

    Object[] message = {
            "Please enter your portfolio name", fields.get(0),
            "Please enter date in format (yyyy-MM-dd)", fields.get(1),
    };

    ArrayList<String> result;
    result = inputFieldHelper(message, fields);
    return result;
  }

  @Override
  public ArrayList<String> getInputForPerformance() throws IllegalArgumentException {
    List<JTextField> fields = new ArrayList<>();
    for (int i = 0; i < 3; i++) {
      fields.add(new JTextField());
    }
    Object[] message = {
            "Please enter your portfolio name", fields.get(0),
            "Please enter start date in format (yyyy-MM-dd)", fields.get(1),
            "Please enter end date in format (yyyy-MM-dd)", fields.get(2)
    };
    ArrayList<String> result;
    result = inputFieldHelper(message, fields);
    return result;
  }

  @Override
  public ArrayList<String> getInvestmentDetails() throws IllegalArgumentException {
    List<JTextField> fields = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      fields.add(new JTextField());
    }
    Object[] message = {
            "Please enter your portfolio name", fields.get(0),
            "Please enter amount you want to invest", fields.get(1),
            "Please enter date (yyyy-MM-dd)", fields.get(2),
            "Please enter the commission fee", fields.get(3),
            "Please enter the number of stocks you want to invest in", fields.get(4)
    };
    ArrayList<String> result;
    result = inputFieldHelper(message, fields);
    return result;
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
        m.put(fields[i].getText().toUpperCase(), Double.valueOf(fields[i + 1].getText()));
      }

    }
    return m;
  }

  @Override
  public ArrayList<String> getDollarCostDetails() throws IllegalArgumentException {
    List<JTextField> fields = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      fields.add(new JTextField());
    }
    Object[] message = {
            "Please enter your portfolio name", fields.get(0),
            "Please enter amount you want to invest", fields.get(1),
            "Please enter the commission fee", fields.get(2),
            "Please enter start date (yyyy-MM-dd)", fields.get(3),
            "Please enter end date (yyyy-MM-dd)", fields.get(4),
            "Please enter the interval at which you want to periodically invest", fields.get(5),
            "Please enter the number of stocks you want to invest in", fields.get(6)
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
            JOptionPane.OK_CANCEL_OPTION);
    int n = 7;
    String[] inputs = new String[n];
    for (int i = 0; i < 7; i++) {
      inputs[i] = "";
    }

    for (int i = 0; i < n; i++) {
      if (option == JOptionPane.OK_OPTION) {
        inputs[i] = fields.get(i).getText();
      }
    }

    for (int i = 0; i < n; i++) {
      if (i == 4) {
        continue;
      }
      if (inputs[i].equals("")) {
        throw new IllegalArgumentException("Input fields cannot be bank");
      }
    }

    ArrayList<String> output = new ArrayList<>();

    for (int i = 0; i < n; i++) {
      output.add(inputs[i]);
    }
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
