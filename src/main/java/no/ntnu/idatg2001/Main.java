package no.ntnu.idatg2001;


import no.ntnu.idatg2001.frontend.view.ApplicationStart;

/**
 * The main class of the application.
 */
public class Main {

  /**
   * The main method of the application.
   * @param args The arguments passed to the application.
   */
  public static void main(String[] args) {
    try {
      ApplicationStart.startApplication(args);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}