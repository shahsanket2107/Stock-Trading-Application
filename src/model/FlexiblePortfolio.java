package model;

import org.codehaus.jackson.map.annotate.JsonDeserialize;

import java.util.List;

@JsonDeserialize(as= FlexiblePortfolioImpl.class)
public interface FlexiblePortfolio {
  String getName();
  List<Stocks> getStocks();
  void setStocks(List<Stocks> stocks);
}
