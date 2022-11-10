import controller.UserControllerImpl;
import model.UserImpl;
import view.ViewImpl;

/**
 * This is the main class which will run the program. Here we are calling the go method of the
 * controller and passing the object of user and view along with it.
 */
public class Main {

  public static void main(String[] args) {
    new UserControllerImpl(System.in, System.out, new UserImpl(),new ViewImpl()).runGo();
  }
}
