package stocks;

public interface User {

  void createPortfolio(String ticker);

  void getValuationAtDate(String date);

  void savePortfolio();

  void loadPortfolio();
}
