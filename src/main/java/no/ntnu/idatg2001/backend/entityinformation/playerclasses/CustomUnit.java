package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.UnitBuilder;

@Entity
public class CustomUnit extends Unit {
  // Add any additional fields or methods specific to the custom unit

  public CustomUnit(String unitName) {
    super(0,0,unitName,0,0); // Call the superclass constructor to set the unit name
    super.setUnitScore(0);
    // Perform any additional custom initialization or set initial values
  }

  public CustomUnit() {

  }
}
