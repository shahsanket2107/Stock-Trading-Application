package view;

import java.io.PrintStream;

/**
 * This is the view class which implements its interface. It has different functions which prints
 * different messages as and when required.
 */

public class ViewImpl implements View {

  private PrintStream out;

  @Override
  public void setStream(PrintStream out) {
    this.out = out;
  }

  @Override
  public void getPortfolioMessage() {
    this.out.println("Portfolio created successfully!!");
  }

  @Override
  public void getMenu() {
    this.out.println();
    this.out.println("Enter 1 for making portfolio");
    this.out.println("Enter 2 to examine the composition of a particular portfolio");
    this.out.println("Enter 3 to determine the total value of portfolio on a certain date");
    this.out.println("Enter 4 to view all portfolio names");
    this.out.println("Enter 5 to load your portfolio");
    this.out.println("Enter 6 to create flexible portfolio");
    this.out.println("Enter 7 to examine composition of flexible portfolio");
    this.out.println("Enter 8 to load a flexible portfolio");
    this.out.println("Enter 9 to buy stocks on a specific date");
    this.out.println("Enter 10 to sell stocks on a specific date");
    this.out.println("Enter 11 to determine the total value of flexible portfolio on a certain " +
        "date");
    this.out.println("Enter 12 to find cost basis of a flexible portfolio on a certain date");
    this.out.println(
        "Enter 13 to view how the portfolio has performed over a period of time using chart");
    this.out.println("Enter q to exit");
  }

  @Override
  public void getAddStockMenu() {
    this.out.println("Enter 1 to add stocks to your portfolio");
    this.out.println("Enter q to exit");
  }

  @Override
  public void getTicker() {
    this.out.println("Enter ticker of stock you want to add to the portfolio: ");
  }

  @Override
  public void getQty() {
    this.out.println("Enter quantity of stocks: ");
  }

  @Override
  public void qtyPositive() {
    this.out.println("Quantity should be a positive value!");
  }

  @Override
  public void qtyInteger() {
    this.out.println("Quantity should be an integer value!");
  }

  @Override
  public void invalidTicker() {
    this.out.println("This ticker does not exist!! Please enter a valid ticker");
  }


  @Override
  public void getPortfolioName() {
    this.out.println("Enter your portfolio name: ");
  }

  @Override
  public void emptyPortfolioMessage() {
    this.out.println("Portfolio name cannot be empty!");
  }

  @Override
  public void getFileName() {
    this.out.println("Enter your file name (with extension): ");
  }

  @Override
  public void getDate() {
    this.out.println("Enter the date (in format yyyy-MM-dd): ");
  }


  @Override
  public void displayMessage(String message) {
    this.out.println(message);
  }


  @Override
  public void invalidDate() {
    this.out.println("Date is not in proper format!!");
  }


  @Override
  public void seeDefault() {
    this.out.println("Invalid input. Please try again!");
  }


  @Override
  public void getName() {
    this.out.println("Please Enter your name:");

  }


  @Override
  public void displayName(String name) {
    this.out.println("Welcome " + name + " !! Please select an option from the menu!!");
  }


  @Override
  public void alreadyExists() {
    this.out.println("The entered portfolio already exists. Try a different name!");
  }

  @Override
  public void portfolioNotExist() {
    this.out.println("The entered portfolio does not exist. Please enter a valid portfolio name");
  }

  @Override
  public void sellStock() {
    this.out.println("Please enter the ticker of stock you want to sell");
  }

  @Override
  public void dataNotFound() {
    this.out.println("Stock market is closed at this date, so please enter a different date!!");
  }

  @Override
  public void getStartDate() {
    this.out.println("Enter the start date (in format yyyy-MM-dd): ");
  }

  @Override
  public void getEndDate() {
    this.out.println("Enter the end date (in format yyyy-MM-dd): ");
  }

}
