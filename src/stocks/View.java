package stocks;

public class View {

  public View() {
  }

  public void viewComposition(StringBuilder composition) {
    System.out.println(composition);
  }
  public void getMenu(){
    System.out.println("Enter 1 for making portfolio");
    System.out.println("Enter 2 to examine the composition of portfolio");
    System.out.println("Enter 3 to determine the total value of portfolio on a certain date");
    System.out.println("Enter 4 to save your portfolio");
    System.out.println("Enter 5 to load your portfolio");
    System.out.println("Enter q to exit");
  }
}
