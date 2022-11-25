import controller.Controller;
import model.UserImpl;
import view.IView;
import view.JFrameView;

/**
 * This is the main class which will run the program. Here we are calling the go method of the
 * controller and passing the object of user and view along with it.
 */
public class Main {

  public static void main(String[] args) {
    //new UserControllerImpl(System.in, System.out, new UserImpl(), new ViewImpl()).runGo();
    Controller controller = new Controller(new UserImpl());
    IView view = new JFrameView();
    controller.setView(view);
  }
}
