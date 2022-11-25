package view;

import controller.Features;
import java.util.ArrayList;

public interface IView {
  void addFeatures(Features features);
  void showOutput(String message);
  ArrayList<String> createPortfolioInput();
}
