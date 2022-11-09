package model;

import org.json.JSONArray;

public class FlexiblePortfolioImpl implements FlexiblePortfolio {

  private String name;
  private JSONArray jsonArray;

  public FlexiblePortfolioImpl(String name, JSONArray jsonArray) {
    this.name = name;
    this.jsonArray = jsonArray;
  }

  @Override
  public String getName() {
    return this.name;
  }

  @Override
  public JSONArray getPortfolio() {
    return this.jsonArray;
  }
}
