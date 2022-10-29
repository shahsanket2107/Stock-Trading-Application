

package stocks;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.spec.ECField;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UserControllerImpl implements UserController {


  final Readable in;
  final Appendable out;

  public UserControllerImpl(Readable in, Appendable out) {
    this.in = in;
    this.out = out;
  }

  public void createPortfolio(String portfolioName) {

    String fileName = "portfolio.xml";
    boolean fileExist = fileExists(fileName);
    File file = new File(fileName);
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = null;
      Element rootElement = null;
      if (!fileExist) {
        doc = dBuilder.newDocument();
        rootElement = doc.createElement("portfolio");
        doc.appendChild(rootElement);
      } else {
        doc = dBuilder.parse(file);
        rootElement = doc.getDocumentElement(); // get the root [text] element
      }

      // portfolio element
      Element portfolio = doc.createElement("portfolio");
      rootElement.appendChild(portfolio);

      // setting attribute to element
      Attr attr = doc.createAttribute("name");
      attr.setValue(portfolioName);
      portfolio.setAttributeNode(attr);

      // carname element
      //perform(doc, portfolio, "AAPL", "20");
      int flag = 0;
      while (true) {
        System.out.println("Enter 1 to add stocks to your portfolio");
        System.out.println("Enter 0 to return to main menu");
        System.out.println("Enter q to exit");
        if (flag == 1) {
          break;
        }
        Scanner scan = new Scanner(this.in);
        switch (scan.next()) {
          case "1" -> {
            System.out.println("Enter ticker of stock you want to add to the portfolio");
            String ticker = scan.next();
            System.out.println("Enter quantity of stocks");
            int qty = scan.nextInt();
            if (ifStocksExist(ticker)) {
              perform(doc, portfolio, ticker, String.valueOf(qty));
            } else {
              System.out.println("This ticker does not exist!! Please enter a valid ticker");
            }

          }
          case "0" -> flag = 1;
          case "q" -> {
            return;
          }
        }
      }

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("portfolio.xml"));
      transformer.transform(source, result);

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void perform(Document doc, Element portfolio, String ticker, String qty) {
    Element stocks = doc.createElement("stocks");
    Attr attrType = doc.createAttribute("parameter");
    attrType.setValue("ticker");
    stocks.setAttributeNode(attrType);
    stocks.appendChild(doc.createTextNode(ticker));
    portfolio.appendChild(stocks);

    Element stocks1 = doc.createElement("stocks");
    Attr attrType1 = doc.createAttribute("parameter");
    attrType1.setValue("quantity");
    stocks1.setAttributeNode(attrType1);
    stocks1.appendChild(doc.createTextNode(qty));
    portfolio.appendChild(stocks1);
  }

  private boolean fileExists(String filePath) {
    if (new File(filePath).isFile()) {
      return new File(filePath).exists();
    }
    return false;
  }

  private boolean isValidFormat(String value) {
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
      date = sdf.parse(value);
      if (!value.equals(sdf.format(date))) {
        date = null;
      }
    } catch (ParseException ex) {
      ex.printStackTrace();
    }
    return date != null;
  }

  public static void perform(String stockSymbol, String date) {
    String apiKey = "FHA1IC5A17Q0SPLG";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + stockSymbol + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      System.out.println(e.getMessage());
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      assert url != null;
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      int index = output.indexOf(date);
      int endIndex = output.indexOf("\n", index);

      String temp = output.substring(index, endIndex);

      String[] res = temp.split(",", 0);

      String closeValue = res[4];
      System.out.println("Closing Value for " + stockSymbol + " on " + date + " is:" + closeValue);
    } catch (Exception e) {
      System.out.println("Please enter a valid ticker symbol!!");
    }
  }

  @Override
  public void go() {

    Scanner scan = new Scanner(this.in);
    while (true) {
      System.out.println("Enter 1 for making portfolio");
      System.out.println("Enter 2 to examine the composition of portfolio");
      System.out.println("Enter 3 to determine the total value of portfolio on a certain date");
      System.out.println("Enter 4 to save your portfolio");
      System.out.println("Enter 5 to load your portfolio");
      System.out.println("Enter q to exit");
      switch (scan.next()) {
        case "1":
          Scanner sc = new Scanner(this.in);
          System.out.println("Enter your portfolio name");
          String portfolioName = sc.next();
          createPortfolio(portfolioName);
          break;
        case "3":
          Scanner s = new Scanner(System.in);
          System.out.println("Enter the ticker: ");
          String stockSymbol = s.nextLine();
          System.out.println("Enter the date: ");
          String date = s.nextLine();
          if (isValidFormat(date)) {
            perform(stockSymbol, date);
          } else {
            System.out.println("Date is not in proper format!!");
          }
          break;

        case "q":
          return;
      }
    }
  }

  private boolean ifStocksExist(String ticker) {
    String apiKey = "FHA1IC5A17Q0SPLG";
    URL url = null;
    try {
      url = new URL("https://www.alphavantage"
          + ".co/query?function=TIME_SERIES_DAILY"
          + "&outputsize=full"
          + "&symbol"
          + "=" + ticker + "&apikey=" + apiKey + "&datatype=csv");
    } catch (MalformedURLException e) {
      System.out.println(e.getMessage());
    }
    InputStream in = null;
    char b = '0';
    try {
      assert url != null;
      in = url.openStream();
      b = (char) in.read();
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
    return b != '{';
  }
}
