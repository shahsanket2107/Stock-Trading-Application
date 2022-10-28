package stocks;

//public class Main {
//
//  public static void main(String[] args) {
//    User user_model = new UserImpl("Deadpool");
//    UserController controller = new UserControllerImpl(user_model, System.in);
//    controller.go();
//  }

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

public class Main {

  public static void main(String argv[]) {
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
      attr.setValue("college_portfolio");
      portfolio.setAttributeNode(attr);

      // carname element
      //perform(doc, portfolio, "AAPL", "20");
      perform(doc, portfolio, "TSLA", "17");
      perform(doc, portfolio, "GOOGL", "45");

      // write the content into xml file
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(doc);
      StreamResult result = new StreamResult(new File("portfolio.xml"));
      transformer.transform(source, result);

      // Output to console for testing
      StreamResult consoleResult = new StreamResult(System.out);
      transformer.transform(source, consoleResult);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static void perform(Document doc, Element portfolio, String ticker, String qty) {
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

  private static boolean fileExists(String filePath) {
    if (new File(filePath).isFile())
      return new File(filePath).exists();
    return false;
  }
}

