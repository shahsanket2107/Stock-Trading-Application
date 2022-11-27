package view;

import controller.Features;
import java.util.ArrayList;
import java.util.Map;

public interface IView {
  void addFeatures(Features features);
  void showOutput(String message);
  ArrayList<String> createPortfolioInput();
  ArrayList<String> getInput();
  ArrayList<String> getInputForPerformance();
  ArrayList<String> getInvestmentDetails();
  Map<String,Double> getInvestmentShares();
  ArrayList<String> getDollarCostDetails();
}
