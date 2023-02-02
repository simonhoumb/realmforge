package ntnu.no.idatg2001.utilities;

/**
 * This class is used to handle all the Valid checks for the application.

 *  @author Eskil Alstad
 *
 *  @version 2022-12-07
 */
public class CheckIfValid {


  /**
   * Check if number is not lower than zero boolean.
   *
   * @param intToCheck the int to check
   * @return the boolean
   */
  public boolean checkIfNumberIsNotLowerThanZero(int intToCheck) {
    return intToCheck >= 0;
  }
}
