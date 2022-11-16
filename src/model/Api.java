package model;

/**
 * This interface is made for calling API's. Now we are calling the alphaVantage API but if you want
 * to make any more different api calls, then it will be in this interface.
 */
public interface Api {

  /**
   * This method takes ticker as input from the user and returns the output from the api as
   * Stringbuilder in csv format.
   *
   * @param ticker the ticker whose value you want to fetch from the api.
   * @return the output from the API as stringbuilder in csv format.
   */
  StringBuilder getApiOutputFromTicker(String ticker);
}
