package stocks;

import java.io.InputStream;

public class UserControllerImpl implements UserController {
  private User user_model;
  private InputStream input;

  public UserControllerImpl(User user_model, InputStream input) {
    this.user_model = user_model;
    this.input = input;
  }

  @Override
  public void go() {

  }
}
