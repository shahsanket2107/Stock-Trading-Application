package model;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;
import org.codehaus.jackson.map.util.JSONPObject;
import org.codehaus.jackson.node.ArrayNode;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.util.DefaultPrettyPrinter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
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
 * This class has all the methods for reading from and writing to a file. Currently, we are
 * supporting xml and json format only but all the file operations will be done here.
 */
public class FileOperationsImpl implements FileOperations {

  @Override
  public void writeToFile(String fileName, String portfolioName, Map<String, Integer> stocks)
          throws IllegalArgumentException {
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
  }

  private void writerJSONHelper(String fileName, String pname) {
    JSONObject result;
    if (!fileExists(fileName)) {
      try {
        result = new JSONObject();
        result.put(pname, null);
        FileWriter writer = new FileWriter(fileName);
        writer.write(result.toJSONString());
        writer.close();
      } catch (Exception e) {
        throw new IllegalArgumentException("Error in creating new file!!");
      }
    }
  }

  @Override
  public void futureDatesStrategyHelper(String fileName, String pname, Map<String, Double> m,
                                        double amount, double commissionFee,
                                        String startDate, String endDate, int interval)
          throws IllegalArgumentException {
    writerJSONHelper(fileName, pname);
    JSONObject result;
    try {
      FileReader reader = new FileReader(fileName);
      JSONParser parser = new JSONParser();
      result = (JSONObject) parser.parse(reader);
      JSONArray values = (JSONArray) result.get(pname);
      if (values == null) {
        values = new JSONArray();
      }
      JSONObject investment = new JSONObject();
      JSONObject map = new JSONObject(m);
      investment.put("startDate", startDate);
      investment.put("endDate", endDate);
      investment.put("interval", interval);
      investment.put("amount", amount);
      investment.put("fractionalShares", map);
      investment.put("commissionFee", commissionFee);
      values.add(investment);
      result.put(pname, values);

    } catch (Exception e) {
      throw new IllegalArgumentException("Error in parsing the file!!");
    }

    try {
      FileWriter writer = new FileWriter(fileName);
      writer.write(result.toJSONString());
      writer.close();
    } catch (Exception e) {
      throw new IllegalArgumentException("Error in writing to file!!");
    }
  }

  @Override
  public void writeToJson(String fileName, String portfolioName, List<Stocks> stocks)
          throws IllegalArgumentException {
    boolean fileExist = fileExists(fileName);
    List<FlexiblePortfolio> result;
    List<FlexiblePortfolio> temp;
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      if (!fileExist) {
        temp = new ArrayList<>();
      } else {
        temp = Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(),
                FlexiblePortfolio[].class));
      }
      result = new ArrayList<>(temp);
      FlexiblePortfolio p = new FlexiblePortfolioImpl(portfolioName, stocks);
      result.add(p);
      writer.writeValue(Paths.get(fileName).toFile(), result);
    } catch (Exception e) {
      throw new IllegalArgumentException("Error in writing to json file!!");
    }
  }

  @Override
  public void editJson(String fileName, String portfolioName, List<Stocks> stocks)
          throws IllegalArgumentException {
    boolean fileExist = fileExists(fileName);
    List<FlexiblePortfolio> temp;
    try {
      ObjectMapper mapper = new ObjectMapper();
      ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());
      if (!fileExist) {
        throw new IllegalArgumentException("File does not exist!!");
      } else {
        temp = Arrays.asList(mapper.readValue(Paths.get(fileName).toFile(),
                FlexiblePortfolio[].class));
      }
      int flg = 0;
      for (FlexiblePortfolio fp : temp) {
        if (fp.getName().equals(portfolioName)) {
          fp.setStocks(stocks);
          flg = 1;
          break;
        }
      }
      List<FlexiblePortfolio> result = new ArrayList<>(temp);
      if (flg == 0) {
        FlexiblePortfolio p = new FlexiblePortfolioImpl(portfolioName, stocks);
        result.add(p);
      }
      writer.writeValue(Paths.get(fileName).toFile(), result);
    } catch (Exception e) {
      e.printStackTrace();
      throw new IllegalArgumentException("Error in writing to json file!!");
    }
  }

  @Override
  public List<FlexiblePortfolio> readFromJson(String fName) throws IllegalArgumentException {
    List<FlexiblePortfolio> portfolios_list;
    try {
      boolean fileExist = fileExists(fName);
      if (!fileExist) {
        throw new IllegalArgumentException("Invalid file name. Please try again!");
      }
      String[] checkJson = fName.split("\\.");
      if (!checkJson[1].equals("json")) {
        throw new IllegalArgumentException("Invalid file format. Only json files can be loaded!");
      }
      ObjectMapper mapper = new ObjectMapper();
      portfolios_list = Arrays.asList(mapper.readValue(Paths.get(fName).toFile(),
              FlexiblePortfolio[].class));
      List<FlexiblePortfolio> result = new ArrayList<>(portfolios_list);
      return result;
    } catch (Exception e) {
      throw new IllegalArgumentException("Error in reading from json file!!!");
    }
  }

  @Override
  public List<Portfolio> readFromFile(String fName) throws IllegalArgumentException {
    List<Portfolio> portfolios_list = new ArrayList<>();
    try {
      boolean fileExist = fileExists(fName);
      if (!fileExist) {
        throw new IllegalArgumentException("Invalid file name. Please try again!");
      }
      String[] checkXML = fName.split("\\.");
      if (!checkXML[1].equals("xml")) {
        throw new IllegalArgumentException("Invalid file format. Only xml files can be loaded!");
      }
      File file = new File(fName);
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
        loadPortfolioHelper(ticker, qty, m, portfolio, portfolios_list);
      }
    } catch (IOException | SAXException | ParserConfigurationException e) {
      throw new IllegalArgumentException(
              "Unable to read xml file!!\n Please check proper xml format and try again!!");
    }
    return portfolios_list;
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
                                   Map<String, Integer> m, Node portfolio, List<Portfolio> portfoliosList) {
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
      portfoliosList.add(p);
    }
  }

  /**
   * This function is a helper function for writing to xml file.
   *
   * @param doc the dom parser for xml.
   * @throws TransformerConfigurationException if there is an error in xml syntax.
   */

  private void writeXMLHelper(Document doc, String fileName) throws
          TransformerConfigurationException {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new java.io.File(fileName));
      transformer.transform(source, result);
    } catch (TransformerException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * This function is used to check if the file exists or not.
   *
   * @param filePath the filePath which needed to be checked if it exists or not.
   * @return true if the filepath exists and false otherwise.
   */
  private boolean fileExists(String filePath) {
    if (new java.io.File(filePath).isFile()) {
      return new java.io.File(filePath).exists();
    }
    return false;
  }

  /**
   * This is a helper function which writes xml file in the form of tags of portfolio, ticker and
   * quantity.
   *
   * @param doc       the xml document where the portfolio is to be written.
   * @param portfolio it's the element or the child of root element which stores tickers and
   *                  quantities of various stocks.
   * @param ticker    the ticker of stock.
   * @param qty       the quantity of stock.
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

}
