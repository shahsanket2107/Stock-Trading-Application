package model;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

/**
 * This class has all the functions of user model. As the user has a portfolio, this class has a
 * portfolio object which calls its methods. This class is called by the controller.
 */

public class UserImpl implements User {

  private final ArrayList<Portfolio> portfolio;
  private String name;

  public UserImpl() {
    this.name = "John Doe";
    this.portfolio = new ArrayList<>();
  }

  public UserImpl(String name, ArrayList<Portfolio> portfolio) {
    this.name = name;
    this.portfolio = portfolio;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public void setName(String name) {
    this.name = name;
  }

  @Override
  public StringBuilder getPortfoliosName() {
    StringBuilder temp = new StringBuilder();
    StringBuilder tp = new StringBuilder();
    Portfolio p;
    tp.append("The list of portfolios is:\n");
    for (Portfolio value : this.portfolio) {
      p = value;
      temp.append(p.getName() + "\n");
    }
    if (temp.toString().equals("")) {
      temp.append("No portfolios exist at this time!! ");
      return temp;
    }
    tp.append(temp);
    return tp;
  }

  @Override
  public void createPortfolio(String portfolioName, Map<String, Integer> stocks)
          throws IllegalArgumentException {
    String fileName = this.name + "_portfolios.xml";
    boolean fileExist = fileExists(fileName);
    File file = new File(fileName);
    try {
      DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
      DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
      Document doc = null;
      Element rootElement = null;
      if (!fileExist) {
        doc = dBuilder.newDocument();
        rootElement = doc.createElement("portfolios");
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
      try {
        writeXMLHelper(doc, fileName);
      } catch (TransformerConfigurationException e) {
        throw new IllegalArgumentException("Error in writing to xml file!!");
      }
    } catch (ParserConfigurationException | IOException | SAXException e) {
      throw new IllegalArgumentException("Error in parsing xml!!");
    }
    portfolio.add(new PortfolioImpl(portfolioName, stocks));
  }

  /**
   * This function is a helper function for writing to xml file
   *
   * @param doc the dom parser for xml
   * @throws TransformerConfigurationException if there is an error in xml syntax.
   */

  private void writeXMLHelper(Document doc, String fileName) throws TransformerConfigurationException {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File(fileName));
      transformer.transform(source, result);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }


  @Override
  public String loadPortfolio(String pfName) throws IllegalArgumentException {
    try {
      boolean fileExist = fileExists(pfName);
      if (!fileExist) {
        return "Invalid file name. Please try again!";
      }
      String[] checkXML = pfName.split("\\.");
      if (!checkXML[1].equals("xml")) {
        return "Invalid file format. Only xml files can be loaded!";
      }
      File file = new File(pfName);
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      doc.getDocumentElement().normalize();
      NodeList nodeList = doc.getElementsByTagName("portfolio");
      if (nodeList.getLength() == 0) {
        throw new IllegalArgumentException("Parsed XML file has no portfolio associated with it!!");
      }
      for (int i = 0; i < nodeList.getLength(); i++) {
        Map<String, Integer> m = new HashMap<>();
        ArrayList<String> ticker = new ArrayList<>();
        ArrayList<String> qty = new ArrayList<>();
        Node portfolio = nodeList.item(i);
        loadPortfolioHelper(ticker, qty, m, portfolio);
      }
    } catch (IOException | SAXException | ParserConfigurationException e) {
      throw new IllegalArgumentException(
              "Unable to read xml file!!\n Please check proper xml format and try again!!");
    }
    return "Portfolio loaded successfully!";
  }

  /**
   * This function is a helper function for loading portfolio.
   *
   * @param ticker    The ticker of stocks in the portfolio.
   * @param qty       The quantities of stocks in portfolio.
   * @param m         the map mapping the ticker to quantity.
   * @param portfolio XML parsing node passed to helper method to write portfolio to xml file.
   */
  private void loadPortfolioHelper(ArrayList<String> ticker, ArrayList<String> qty,
                                   Map<String, Integer> m, Node portfolio) {
    String name;
    String type;
    if (portfolio.getNodeType() == Node.ELEMENT_NODE) {
      Element portfolioElement = (Element) portfolio;
      name = portfolioElement.getAttribute("name");
      NodeList portfolio_details = portfolio.getChildNodes();
      for (int j = 0; j < portfolio_details.getLength(); j++) {
        Node detail = portfolio_details.item(j);
        if (detail.getNodeType() == Node.ELEMENT_NODE) {
          Element detailElement = (Element) detail;
          type = detailElement.getAttribute("parameter");
          if (type.equals("ticker")) {
            ticker.add(detailElement.getTextContent());
          }
          if (type.equals("quantity")) {
            qty.add(detailElement.getTextContent());
          }
        }
      }
      for (int k = 0; k < ticker.size(); k++) {
        m.put(ticker.get(k), Integer.valueOf(qty.get(k)));
      }
      Portfolio p = new PortfolioImpl(name, m);
      this.portfolio.add(p);
    }
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

  /**
   * This is a helper function which writes xml file in the form of tags of portfolio, ticker and
   * quantity
   *
   * @param doc       the xml document where the portfolio is to be written.
   * @param portfolio it's the element or the child of root element which stores tickers and
   *                  quantities of various stocks.
   * @param ticker    the ticker of stock
   * @param qty       the quantity of stock
   */
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

  /**
   * This function is used to check if the file exists or not
   *
   * @param filePath the filePath which needed to be checked if it exists or not
   * @return true if the filepath exists and false otherwise.
   */
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
  public StringBuilder getTotalValuation(String date, String pName) {
    String temp_date = date.replaceAll("[\\s\\-()]", "");
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    LocalDateTime now = LocalDateTime.now();
    String curr_date = dtf.format(now).replaceAll("[\\s\\-()]", "");
    if (Integer.parseInt(temp_date) >= Integer.parseInt(curr_date)) {
      return new StringBuilder(
              "Date cannot be greater or equal to current date. Try a different date");
    }
    if (Integer.parseInt(temp_date) <= 20000101) {
      return new StringBuilder("Date should be more than 1st January 2000. Try a different date");
    }
    StringBuilder temp = new StringBuilder();
    Portfolio p;
    for (Portfolio value : this.portfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        temp.append("Portfolio_Name: ").append(p.getName());
        temp.append("\n");
        temp.append("Portfolio_Valuation at ").append(date).append(" is : $ ")
                .append(p.getValuationAtDate(date));
        temp.append("\n\n");
      }
    }
    if (temp.toString().equals("")) {
      temp.append("Given portfolio doesn't exist!!");
    }
    return temp;
  }

  @Override
  public StringBuilder getPortfolioComposition(String pName) {
    StringBuilder temp = new StringBuilder();
    Portfolio p;
    int flg = 0;
    for (Portfolio value : this.portfolio) {
      p = value;
      if (p.getName().equals(pName)) {
        flg = 1;
        temp.append("Portfolio_Name: ").append(p.getName());
        temp.append("\n");
        Map<String, Integer> m = p.getStocks();
        for (Map.Entry<String, Integer> entry : m.entrySet()) {
          temp.append("{\n");
          temp.append("\tStock_Ticker: ").append(entry.getKey()).append("\n");
          temp.append("\tQuantity: ").append(entry.getValue()).append("\n");
          temp.append("}\n");
        }
        temp.append("\n\n");
        break;
      }
    }
    if (flg == 0) {
      temp.append(
              "The given portfolio name does not exist!!\nPlease enter a valid portfolio name!!");
    }
    return temp;
  }

  @Override
  public boolean checkPortfolioExists(String pName) {
    int flg = 0;
    for (Portfolio value : this.portfolio) {
      if (value.getName().equals(pName)) {
        flg = 1;
        break;
      }
    }
    return flg == 1;
  }
}
