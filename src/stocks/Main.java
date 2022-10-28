package stocks;

import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) {
    User user_model = new UserImpl("Deadpool");
    new UserControllerImpl(new InputStreamReader(System.in), System.out,user_model).go();
  }

}
