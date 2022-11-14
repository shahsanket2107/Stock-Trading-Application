import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class graph {

  public static void main(String[] args) throws ParseException {
    String startDate = "2022-10-21";
    String endDate = "2023-10-31";
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    Date s = sdf.parse(startDate);
    Date e = sdf.parse(endDate);
    long diff = TimeUnit.DAYS.convert(Math.abs(e.getTime() - s.getTime()),
        TimeUnit.MILLISECONDS);

    long timeLine = diff;
    int week = 0;
    int month = 0;
    int year = 0;
    String wd = "";
    String mm = "";
    String yyyy = "";
    Calendar c = Calendar.getInstance();
    c.setTime(e);
    if (diff > 30) {
      timeLine = diff / 7;
      week = 1;
      wd =  returnWeekDay(c.get(Calendar.WEEK_OF_MONTH));
      if (timeLine > 20) {
        timeLine = timeLine / 4;
        month = 1;
        mm = String.valueOf(c.get(Calendar.MONTH));
        if (timeLine > 12) {
          timeLine = timeLine / 12;
          year = 1;
          yyyy = String.valueOf(c.get(Calendar.YEAR));
        }
      }
    }
//    System.out.println(wd);
//    System.out.println(mm);
//    System.out.println(yyyy);
    Map<String,Double> m = new HashMap<>();
    m.put("Mon",100.2);
    m.put("Tue",900.7);
    m.put("Wed",200.89);
    m.put("Thu",789.7);
    m.put("Fri",399.12);
    Double maxValueInMap = (Collections.max(m.values()));
    Double minValueInMap = (Collections.min(m.values()));
    System.out.println(maxValueInMap);
    System.out.println(minValueInMap);

  }
   public static String returnWeekDay(int wd){
    if(wd==1)
      return "Mon";
    if(wd==2)
      return "Tue";
    if(wd==3)
      return "Wed";
    if(wd==4)
      return "Thu";
    if(wd==5)
      return "Fri";
    if(wd == 6)
      return "Sat";
    return "Sun";
  }

}
