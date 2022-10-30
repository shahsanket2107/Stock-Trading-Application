package view;

import java.util.Scanner;

public class View {

  public View() {
  }

  public void viewComposition(StringBuilder composition) {
    System.out.println(composition);
  }

  public void getMenu() {
    System.out.println();
    System.out.println("Enter 1 for making portfolio");
    System.out.println("Enter 2 to examine the composition of a particular portfolio");
    System.out.println("Enter 3 to determine the total value of portfolio on a certain date");
    System.out.println("Enter 4 to view all portfolio names");
    System.out.println("Enter 5 to load your portfolio");
    System.out.println("Enter q to exit");
  }

  public void getAddStockMenu() {
    System.out.println("Enter 1 to add stocks to your portfolio");
    System.out.println("Enter q to exit");
  }

  public String getTicker() {
    System.out.println("Enter ticker of stock you want to add to the portfolio");
    Scanner scan = new Scanner(System.in);
    return scan.nextLine();
  }

  public int getQty() {
    Integer input = null;
    Scanner sc = new Scanner(System.in);
    do {
      System.out.println("Enter quantity of stocks");
      String s = sc.nextLine();
      try {
        input = Integer.parseInt(s);
      } catch (NumberFormatException e) {
        System.out.println("Quantity should be an integer value");
      }
    } while (input == null);

    return input;
  }

  public void invalidTicker() {
    System.out.println("This ticker does not exist!! Please enter a valid ticker");
  }

  public String getPortfolioName() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter your portfolio name");
    return sc.nextLine();
  }

  public String getFileName() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Enter your file name");
    return sc.nextLine();
  }

  public String getDate() {
    Scanner s = new Scanner(System.in);
    System.out.println("Enter the date (in format yyyy-MM-dd): ");
    return s.nextLine();
  }

  public void displayResult(StringBuilder result) {
    System.out.println(result);
  }

  public void invalidDate() {
    System.out.println("Date is not in proper format!!");
  }

  public void seeDefault() {
    System.out.println("Invalid input. Please try again!");
  }
  public void getLoadPortfolio(String output){
    System.out.println(output);
  }
  public String getName(){
    System.out.println("Please Enter your name:");
    Scanner s=new Scanner(System.in);
    return s.nextLine();
  }

  public void displayName(String name){
    System.out.println("Welcome "+name+" !!\nPlease select an option from the menu!!\n");
  }
}
