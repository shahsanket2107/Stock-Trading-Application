import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;
import model.User;
import model.UserImpl;

public class graph {

  public static void main(String[] args) throws ParseException {
    String startDate = "2022-10-15";
    String endDate = "2022-10-29";
    User user = new UserImpl();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date s = sdf.parse(startDate);
    Date e = sdf.parse(endDate);
    long diff = TimeUnit.DAYS.convert(Math.abs(e.getTime() - s.getTime()),
        TimeUnit.MILLISECONDS);

    long timeLine = diff;
    int week = 0;
    int month = 0;
    int year = 0;

    Calendar c = Calendar.getInstance();
    c.setTime(s);
    ArrayList<String> dates = new ArrayList<>();
    if (diff > 30) {
      timeLine = diff / 7;
      week = 1;
      if (timeLine > 20) {
        timeLine = timeLine / 4;
        month = 1;
        if (timeLine > 15) {
          timeLine = timeLine / 12;
          year = 1;
        }
      }
    }
    if (year == 1) {
      for (int i = 0; i <= timeLine; i++) {
        dates.add(sdf.format(c.getTime()));
        c.add(Calendar.YEAR, 1);
      }
    } else if (month == 1) {
      for (int i = 0; i <= timeLine; i++) {
        dates.add(sdf.format(c.getTime()));
        c.add(Calendar.MONTH, 1);
      }
    } else if (week == 1) {
      for (int i = 0; i <= timeLine; i++) {
        dates.add(sdf.format(c.getTime()));
        c.add(Calendar.WEEK_OF_YEAR, 1);
      }
    } else {
      for (int i = 0; i <= timeLine; i++) {
        dates.add(sdf.format(c.getTime()));
        c.add(Calendar.DATE, 1);
      }
    }
    String temp_date;
    Map<String, Double> m = new TreeMap<>();
    for (int i = 0; i < dates.size(); i++) {
      temp_date = (dates.get(i));
      //System.out.println(temp_date);
      user.loadFlexiblePortfolio("sam_portfolios.json");
      String value;
      Double d;
      value = String.valueOf(user.getFlexiblePortfolioTotalValuation(temp_date, "p1"));
      if (value.charAt(0) == 'S') {
        d = 0.0;
      } else {
        try {
          d = Double.parseDouble(value.substring(value.lastIndexOf(" ") + 1));
        } catch (Exception exception) {
          d = 0.0;
        }
      }
      m.put(dates.get(i), d);
    }

    Double maxValueInMap = (Collections.max(m.values()));
    Double minValueInMap = (Collections.min(m.values()));
//    System.out.println(maxValueInMap);
//    System.out.println(minValueInMap);
    double print;
    int scale = (int) ((maxValueInMap - minValueInMap) / 10);
    StringBuilder star= new StringBuilder();
    for (Entry<String, Double> entry : m.entrySet()) {
      print = entry.getValue() / scale;
      int p = (int) Math.round(print);
      System.out.print(entry.getKey());
      System.out.print(": ");
      StringBuilder temp_star=new StringBuilder();
      if(p==0){
        System.out.print(star);
      }
      else {
        for (int i = 1; i <= p; i++) {
          System.out.print("*");
          temp_star.append("*");
        }
        star = temp_star;
      }
      System.out.println();
    }
    System.out.println("Scale: * = $" + scale);
  }

}
