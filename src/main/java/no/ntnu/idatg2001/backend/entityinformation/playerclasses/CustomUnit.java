package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The CustomUnit class represents a custom unit.
 */
@Entity
public class CustomUnit extends Unit {

  /**
   * Creates a CustomUnit with the specified unit name.
   *
   * @param unitName the unit name
   */
  public CustomUnit(String unitName) {
    super(0, 0, unitName, 0, 0); // Call the superclass constructor to set the unit name
    super.setUnitScore(0);
    // Perform any additional custom initialization or set initial values
  }

  /**
   * Creates a CustomUnit with the default unit name of "CustomUnit".
   */
  public CustomUnit() {

  }
}
