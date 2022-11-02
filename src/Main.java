import controller.UserControllerImpl;
import model.UserImpl;
import view.ViewImpl;

public class Main {

  public static void main(String[] args) {
    new UserControllerImpl(System.in, System.out, new UserImpl(),
            new ViewImpl()).go();
  }
}
