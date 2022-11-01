package model;

public interface Stocks {
  double getValuationFromDate(int quantity, String date) throws IllegalArgumentException;

}
