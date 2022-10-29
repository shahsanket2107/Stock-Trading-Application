package stocks;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
  public StringBuilder getTotalValuation(String date) {
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

  @Override
  public void loadPortfolio() {
    try {
      File file = new File("portfolio.xml");
      DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
      DocumentBuilder db = dbf.newDocumentBuilder();
      Document doc = db.parse(file);
      doc.getDocumentElement().normalize();
      Portfolio p;
      NodeList nodeList = doc.getElementsByTagName("portfolio");
      for (int i = 0; i < nodeList.getLength(); i++) {
        String name;
        String type;
        Map<String, Integer> m = new HashMap<>();
        ArrayList<String> ticker = new ArrayList<>();
        ArrayList<String> qty = new ArrayList<>();
        Node portfolio = nodeList.item(i);
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
          p = new PortfolioImpl(name, m);
          this.portfolio.add(p);
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
