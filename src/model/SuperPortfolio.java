package model;

/**
 * This interface is the main super portfolio interface. Now all the portfolios will extend this
 * interface. This interface will have all common functionalities of different portfolios.
 * Right now flexible and inflexible portfolios extend this, later on if new portfolios are added
 * then all of them will extend this.
 */
public interface SuperPortfolio {

  /**
   * This function is a getter to get the name of any portfolio.
   *
   * @return the name of any portfolio
   */
  String getName();
}
