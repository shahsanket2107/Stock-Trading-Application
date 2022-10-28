package stocks;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Main {

  public static void main(String[] args) {
   new UserControllerImpl(new InputStreamReader(System.in), System.out).go();
  }
}
