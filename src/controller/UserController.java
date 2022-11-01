package controller;

/**
 * This is the interface for controller. Controller takes input from the user and delegates it to
 * model and view. It is the link between main and view & model. Here we are defining user
 * controller as it will only interact with user model and the other models are called from there
 * later.
 */
public interface UserController {

  /**
   * This is the method of the controller where we define a switch case and give user a text-based
   * interface. All the model and view calls would be done from here.
   */
  void go();
}

