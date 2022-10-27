package stocks;

public class Main {

  public static void main(String[] args) {
    User user_model = new UserImpl("Deadpool");
    UserController controller = new UserControllerImpl(user_model, System.in);
    controller.go();
  }

}
