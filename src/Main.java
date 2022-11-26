import controller.UserControllerImpl;
import model.UserImpl;
import model.UserModelExtension;
import model.UserModelExtensionImpl;
import view.ViewImpl;

/**
 * This is the main class which will run the program. Here we are calling the go method of the
 * controller and passing the object of user and view along with it.
 */
public class Main {

  public static void main(String[] args) {
    UserModelExtension user = new UserModelExtensionImpl();
    new UserControllerImpl(System.in, System.out, user, new ViewImpl()).runGo();
  }
}
