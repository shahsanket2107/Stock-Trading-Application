import controller.Controller;
import controller.UserControllerImpl;
import java.util.Scanner;
import model.UserImpl;
import model.UserModelExtensionImpl;
import view.IView;
import view.JFrameView;
import view.ViewImpl;

/**
 * This is the main class which will run the program. Here we are calling the go method of the
 * controller and passing the object of user and view along with it.
 */
public class Main {

  /**
   * The main class to run the program. The user gets the option of text-based view or gui view
   * here.
   *
   * @param args argument passed to main method.
   */
  public static void main(String[] args) {
    System.out.println("Enter 1 for text-based view and 2 for GUI view: ");
    Scanner scan = new Scanner(System.in);
    switch (scan.nextLine()) {
      case "1":
        new UserControllerImpl(System.in, System.out, new UserImpl(), new ViewImpl()).runGo();
        break;
      case "2":
        System.out.println("Please enter your name: ");
        String name = scan.nextLine();
        Controller controller = new Controller(new UserModelExtensionImpl(), name);
        IView view = new JFrameView(name);
        controller.setView(view);
        break;
      default:
        System.out.println("Invalid input!");
    }
  }
}
