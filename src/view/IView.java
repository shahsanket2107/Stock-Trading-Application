package view;

import controller.Features;
import java.util.ArrayList;
import java.util.Map;

public interface IView {
  void addFeatures(Features features);
  void showOutput(String message);
  ArrayList<String> createPortfolioInput() throws IllegalArgumentException;
  ArrayList<String> getInput() throws IllegalArgumentException;
  ArrayList<String> getInputForPerformance() throws IllegalArgumentException;
  ArrayList<String> getInvestmentDetails() throws IllegalArgumentException;
  Map<String,Double> getInvestmentShares() throws IllegalArgumentException;
  ArrayList<String> getDollarCostDetails() throws IllegalArgumentException;
  void showBlank();
}
