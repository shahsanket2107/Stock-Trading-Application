package stocks;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

public class UserImpl implements User {

  private String name;
  private ArrayList<Portfolio> portfolio;

  public UserImpl(String name) {
    this.name = name;
    this.portfolio = new ArrayList<>();
  }

  @Override
  public void createPortfolio(String portfolioName, Map<String, Integer> stocks) {
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
        rootElement = doc.getDocumentElement();
      }

      Element portfolio = doc.createElement("portfolio");
      rootElement.appendChild(portfolio);

      Attr attr = doc.createAttribute("name");
      attr.setValue(portfolioName);
      portfolio.setAttributeNode(attr);

      for (Map.Entry<String, Integer> entry : stocks.entrySet()) {
        perform(doc, portfolio, entry.getKey(), String.valueOf(entry.getValue()));
      }

      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("portfolio.xml"));
      transformer.transform(source, result);

    } catch (Exception e) {
      e.printStackTrace();
    }
    portfolio.add(new PortfolioImpl(portfolioName, stocks));
  }

  @Override
  public void getValuationAtDate(String date) {

  }

  @Override
  public void savePortfolio() {

  }

  @Override
  public void loadPortfolio() {

  }

  public static boolean ifStocksExist(String ticker) {

    for(Ticker i: Ticker.values()){
      if (!i.name().equalsIgnoreCase(String.valueOf(ticker))) {
        return false;
      }
    }
    return  true;

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

  @Override
  public void getClosingValue(String stockSymbol, String date) {
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

  private boolean fileExists(String filePath) {
    if (new File(filePath).isFile()) {
      return new File(filePath).exists();
    }
    return false;
  }

  @Override
  public boolean isValidFormat(String value) {
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

  @Override
  public StringBuilder getPortfolioComposition() {
    StringBuilder temp = new StringBuilder();
    Portfolio p;
    for (int i = 0; i < this.portfolio.size(); i++) {
      p = this.portfolio.get(i);
      temp.append("Portfolio_Name: " + p.getName());
      temp.append("\n");
      Map<String, Integer> m = p.getStocks();
      for (Map.Entry<String, Integer> entry : m.entrySet()) {
        temp.append("{\n");
        temp.append("\tStock_Ticker: " + entry.getKey() + "\n");
        temp.append("\tQuantity: " + entry.getValue() + "\n");
        temp.append("}\n");
      }

      temp.append("\n\n");
    }
    if (temp.toString().equals("")) {
      temp.append("Please add a portfolio first!!");
    }
    return temp;
  }
}
