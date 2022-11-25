package view;

import controller.Features;
import java.awt.FlowLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class JFrameView  extends JFrame implements IView{

  private JButton cpBtn;
  private JButton bsBtn;
  private JButton ssBtn;
  private JButton cbBtn;
  private JButton vBtn;
  private JButton lpBtn;

  public JFrameView() {

    setTitle("ASSIGNMENT 6");
    setSize(400, 400);
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //mainScrollPane = new JScrollPane(mainPanel);
    add(mainPanel);

    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(
        BorderFactory.createTitledBorder("Choose any option from the menu :"));
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

    lpBtn = new JButton("Find cost basis of a portfolio");
    lpBtn.setActionCommand("Cost Basis");
    loadPortfolioPanel.add(lpBtn);
    setVisible(true);
  }
  @Override
  public void addFeatures(Features features) {
    cpBtn.addActionListener(evt -> features.createPortfolio());
  }

  @Override
  public void showOutput(String message) {
    JOptionPane.showMessageDialog(null,message);
  }

  @Override
  public ArrayList<String> createPortfolioInput() {
    JTextField field1 = new JTextField();
    JTextField field2 = new JTextField();
    JTextField field3 = new JTextField();
    JTextField field4 = new JTextField();
    Object[] message = {
        "Please enter your portfolio name", field1,
        "Please enter ticker of stock you want to add to portfolio", field2,
        "Please enter quantity of stocks", field3,
        "Please enter date at which you want to buy (yyyy-MM-dd)", field4,
    };
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String ticker="";
    String qty="";
    String date="";
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      ticker = field2.getText();
      qty = field3.getText();
      date = field4.getText();
    }
    ArrayList<String> output = new ArrayList<>();
    output.add(pName);
    output.add(ticker);
    output.add(qty);
    output.add(date);
    return output;
  }
}
