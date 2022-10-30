
import controller.UserControllerImpl;
import java.io.InputStreamReader;
import model.UserImpl;
import view.View;

public class Main {

  public static void main(String[] args) {
    new UserControllerImpl(new InputStreamReader(System.in), System.out, new UserImpl(),
        new View()).go();
  }
}
