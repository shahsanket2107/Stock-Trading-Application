package stocks;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.io.File;
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
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;

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

  @Override
  public boolean ifStocksExist(String ticker) {
    boolean flag = false;
    for (Ticker i : Ticker.values()) {
      if (i.name().equalsIgnoreCase(String.valueOf(ticker))) {
        flag = true;
        break;
      }
    }
    return flag;
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
  public StringBuilder getTotalValuation(String date)  {
    String temp_date = date.replaceAll("[\\s\\-()]", "");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
    if(Integer.valueOf(temp_date)>=Integer.valueOf(curr_date)){
      return new StringBuilder("Date cannot be greater or equal to current date. Try a different date");
    }
    if(Integer.valueOf(temp_date)<=20000101){
      return new StringBuilder("Date should be more than 1st January 2000. Try a different date");
    }
    StringBuilder temp = new StringBuilder();
    Portfolio p;
    for (int i = 0; i < this.portfolio.size(); i++) {
      p = this.portfolio.get(i);
      temp.append("Portfolio_Name: " + p.getName());
      temp.append("\n");
      temp.append("Portfolio_Valuation at " + date + " : " + p.getValuationAtDate(date));
      temp.append("\n\n");
    }
    if (temp.toString().equals("")) {
      temp.append("Please add a portfolio first!!");
    }
    return temp;
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
