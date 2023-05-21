package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class Custom extends Unit {
  public Custom(String entityName) {
    super(0, 0, entityName, 0, 0);
    super.setPlayerClass(PlayerClass.CUSTOM);
  }

  public Custom() {
    // Default constructor
  }
}

