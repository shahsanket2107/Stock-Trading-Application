import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import model.Stocks;
import model.StocksImpl;
import model.User;
import model.UserImpl;
import view.View;
import view.ViewImpl;


/**
 * This example shows the different user interface elements in Java Swing. Please use these examples
 * as guidelines only to see how to use them. This example has not been designed very well, it is
 * only meant to illustrate code snippets.
 * <p>
 * Feel free to try out different modifications to see how the program changes
 */

public class gui extends JFrame implements ActionListener, ItemListener, ListSelectionListener {

  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private final User user;
  private final View view;
  private String name = "Strange";

  public gui(User user, View view) {
    this.user = user;
    this.view = view;
    user.setName(name);
    setTitle("ASSIGNMENT 6");
    setSize(400, 400);
    mainPanel = new JPanel();
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

    JButton cpBtn = new JButton("Create a flexible portfolio");
    //cpBtn.setBounds(100,10,200,40);
    cpBtn.setActionCommand("Create portfolio");
    cpBtn.addActionListener(this);
    createPortfolioPanel.add(cpBtn);

    JPanel buyStocksPanel = new JPanel();
    buyStocksPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(buyStocksPanel);

    JButton bsBtn = new JButton("Buy stocks");
    bsBtn.setActionCommand("Buy Stocks");
    bsBtn.addActionListener(this);
    buyStocksPanel.add(bsBtn);

    JPanel sellStocksPanel = new JPanel();
    sellStocksPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(sellStocksPanel);

    JButton ssBtn = new JButton("Sell stocks");
    ssBtn.setActionCommand("Sell Stocks");
    ssBtn.addActionListener(this);
    sellStocksPanel.add(ssBtn);

    JPanel findCostBasisPanel = new JPanel();
    findCostBasisPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(findCostBasisPanel);

    JButton cbBtn = new JButton("Find cost basis of a portfolio");
    cbBtn.setActionCommand("Cost Basis");
    cbBtn.addActionListener(this);
    findCostBasisPanel.add(cbBtn);

    JPanel findValuePanel = new JPanel();
    findValuePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(findValuePanel);

    JButton vBtn = new JButton("Find value of a portfolio");
    vBtn.setActionCommand("Value");
    vBtn.addActionListener(this);
    findValuePanel.add(vBtn);

    JPanel loadPortfolioPanel = new JPanel();
    loadPortfolioPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(loadPortfolioPanel);

    JButton lpBtn = new JButton("Find cost basis of a portfolio");
    lpBtn.setActionCommand("Cost Basis");
    lpBtn.addActionListener(this);
    loadPortfolioPanel.add(lpBtn);
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
  private void createPortfolio(){
    List<Stocks> stocks = new ArrayList<>();
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
    int option = JOptionPane.showConfirmDialog(null, message, "Enter all your values",
        JOptionPane.OK_CANCEL_OPTION);
    String pName = "";
    String ticker;
    int qty;
    String date;
    if (option == JOptionPane.OK_OPTION) {
      pName = field1.getText();
      ticker = field2.getText();
      qty = Integer.parseInt(field3.getText());
      date = field4.getText();
      boolean check=true;
      try {
        check = dateFormatHelper(date);
      }
      catch(Exception e){
        JOptionPane.showMessageDialog(null,e.toString());
      }
      int chk = user.checkPortfolioExists(pName);
      if(chk == 2){
        JOptionPane.showMessageDialog(null, "Portfolio with given name already exists");
      }
      else if (!user.ifStocksExist(ticker)) {
        JOptionPane.showMessageDialog(null, "Ticker is invalid!");
      } else if (qty < 0) {
        JOptionPane.showMessageDialog(null, "Quantity must be positive!");
      } else {
        ticker = ticker.toUpperCase();
        if (user.isValidFormat(date) && user.validateDateAccToApi(ticker, date) && check) {
          Stocks s = new StocksImpl(date, ticker, qty);
          stocks.add(s);
        } else if (!user.isValidFormat(date)) {
          JOptionPane.showMessageDialog(null, "Date is not in proper format!!");
        } else if (!user.validateDateAccToApi(ticker, date)) {
          JOptionPane.showMessageDialog(null,
              "Stock market is closed at this date, so please enter a different date!!");
        }
        try {
          user.createFlexiblePortfolio(pName, stocks);
          JOptionPane.showMessageDialog(null, "Portfolio created successfully!");
        }
        catch (Exception e){
          JOptionPane.showMessageDialog(null,e.toString());
        }
      }

    }
  }

  public static void main(String[] args) {
//    SwingFeaturesFrame.setDefaultLookAndFeelDecorated(false);
//    SwingFeaturesFrame frame = new SwingFeaturesFrame();
//
//    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    frame.setVisible(true);
//
//    try {
//      // Set cross-platform Java L&F (also called "Metal")
//      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//
//    } catch (UnsupportedLookAndFeelException e) {
//      // handle exception
//    } catch (ClassNotFoundException e) {
//      // handle exception
//    } catch (InstantiationException e) {
//      // handle exception
//    } catch (IllegalAccessException e) {
//      // handle exception
//    } catch (Exception e) {
//    }
    gui.setDefaultLookAndFeelDecorated(false);
    gui frame = new gui(new UserImpl(), new ViewImpl());

    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.setVisible(true);
//
//    final JFrame loginFrame = new JFrame("Login window");
//    //add content to the login frame
//    loginFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//    loginFrame.addWindowListener(new WindowAdapter() {
//      @Override
//      public void windowDeactivated(final WindowEvent e) {
//        super.windowDeactivated(e);
//        //test if login successful
//        frame.setVisible(true);
//      }
//    });
//    loginFrame.setVisible(true);

  }

  @Override
  public void actionPerformed(ActionEvent e) {
    switch (e.getActionCommand()) {
      case "Create portfolio":
       createPortfolio();
       break;

    }
  }

  @Override
  public void itemStateChanged(ItemEvent e) {

  }

  @Override
  public void valueChanged(ListSelectionEvent e) {

  }
}
