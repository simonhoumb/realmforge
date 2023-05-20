package no.ntnu.idatg2001.backend.entityinformation;

import java.util.HashMap;
import no.ntnu.idatg2001.backend.entityinformation.playerclasses.Custom;

/**
 * Enum class where The Class of the player is retrieved.


 * @author Eskil Alstad
 */
public enum PlayerClass {

  MAGE("Mage", 1),
  ROGUE("Rogue", 2),
  WARRIOR("Warrior", 3),
  RANGER("Ranger", 4),
  CUSTOM("Custom", 5);

  private static final HashMap<String, PlayerClass> BY_CLASSNAME = new HashMap<>();
  private static final HashMap<Integer, PlayerClass> BY_CLASS_NUMBER = new HashMap<>();

  static {
    for (PlayerClass playerClass : values()) {
      BY_CLASSNAME.put(playerClass.className, playerClass);
      BY_CLASS_NUMBER.put(playerClass.classNumber, playerClass);
    }
  }

  private final String className;
  private final int classNumber;


  /**
   * The constructor of the class.

   * @param className the class of the enum as a String.
   * @param classNumber the number to the enum as an int.
   */
  PlayerClass(String className, int classNumber) {
    this.className = className;
    this.classNumber = classNumber;
  }

  /**
   * Value of player class.
   *
   * @param className the class name
   * @return the player class
   */
  public static PlayerClass valueOfPlayerClass(String className) {
    return BY_CLASSNAME.get(className);
  }

  /**
   * Value of class number player class.
   *
   * @param classNumber the class number
   * @return the player class
   */
  public static PlayerClass valueOfClassNumber(Integer classNumber) {
    return BY_CLASS_NUMBER.get(classNumber);
  }

  /**
   * Gets class name.
   *
   * @return the class name
   */
  public String getClassName() {
    return className;
  }

  /**
   * Gets class number.
   *
   * @return the class number
   */
  public int getClassNumber() {
    return classNumber;
  }
}
