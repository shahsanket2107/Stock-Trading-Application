package stocks;

import java.io.InputStreamReader;

public class Main {

  public static void main(String[] args) {
    new UserControllerImpl(new InputStreamReader(System.in), System.out).go();
  }

}
