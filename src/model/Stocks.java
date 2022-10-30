package model;

import java.io.IOException;

public interface Stocks {
  double getValuationFromDate(int quantity, String date) throws IOException;

}
