import javax.sound.sampled.Port;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import stocks.Portfolio;
import stocks.Stocks;
import stocks.StocksImpl;
import stocks.User;
import stocks.UserController;
import stocks.UserControllerImpl;
import stocks.UserImpl;


public class StocksTest {

  @Test
  public void testUserController() {
    InputStream input = null;
    int num1 = 2;
    int num2 = 30;
    String in = "" + num1 + " " + num2;
    input = new ByteArrayInputStream(in.getBytes());
    User user = new UserImpl("Sanket");
    UserController controller = new UserControllerImpl(user, input);
    controller.go();
  }
  @Test
  public void testMain(){
    User user_model = new UserImpl("Deadpool");
    UserController controller = new UserControllerImpl(user_model, System.in);
    controller.go();
  }
}