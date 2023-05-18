package no.ntnu.idatg2001.backend.utility;

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

  public boolean isInteger(String value) {
    return value.matches("[0-9]+") && !value.isEmpty() && !value.isBlank();
  }
}
