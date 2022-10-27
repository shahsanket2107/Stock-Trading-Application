package stocks;

import java.util.ArrayList;

public class UserImpl implements User {

  final String name;
  final ArrayList<Portfolio> portfolio;

  public UserImpl(String name) {
    this.name = name;
    this.portfolio =  new ArrayList<>();
  }

  @Override
  public void createPortfolio(String ticker) {

  }

  @Override
  public void getValuationAtDate(String date) {

  }

  @Override
  public void savePortfolio() {

  }

  @Override
  public void loadPortfolio() {

  }
}
