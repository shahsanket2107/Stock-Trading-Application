package stocks;

import java.io.InputStream;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class AlphaVantageDemo {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter the ticker: ");
    String stockSymbol = sc.nextLine();
    System.out.println("Enter the date: ");
    String date = sc.nextLine();
    if (isValidFormat("yyyy-mm-dd", date)) {
      perform(stockSymbol, date);
    } else {
      System.out.println("Date is not in proper format!!");
    }
  }

  public static boolean isValidFormat(String format, String value) {
    Date date = null;
    try {
      SimpleDateFormat sdf = new SimpleDateFormat(format);
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
    } catch (Exception e) {
      System.out.println("Please Enter a valid ticker symbol!!");
    }

    InputStream in = null;
    StringBuilder output = new StringBuilder();

    try {
      in = url.openStream();
      int b;

      while ((b = in.read()) != -1) {
        output.append((char) b);
      }
      int index = output.indexOf(date);
      int endIndex = output.indexOf("\n", index);
      //System.out.println("Return value: ");
      //System.out.println(index + " " + endIndex);
      //System.out.println("Output is:");
      //System.out.println(output);
      String temp = output.substring(index, endIndex);
      //System.out.println(temp);
      String[] res = temp.split(",", 0);
      //for (String myStr : res) {
      //  System.out.println(myStr);
      //}
      String closeValue = res[4];
      System.out.println("Closing Value for " + stockSymbol + " on " + date + " is:" + closeValue);
    } catch (Exception e) {
      System.out.println("Please enter a valid ticker symbol!!");
    }
  }
}
