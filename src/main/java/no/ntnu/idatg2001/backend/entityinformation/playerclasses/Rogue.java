package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;

/**
 * The Rogue class represents a Rogue.
 */
@Entity
public class Rogue extends Unit {

  /**
   * Creates a Rogue with the specified entity name.
   *
   * @param playerName the entity name
   */
  public Rogue(String playerName) {
    super(75, 75, playerName, 0, 75);
    super.addToInventory("Dagger");
    super.addToInventory("HealthPotion");
    super.addToInventory("PoisonBottle");
    super.addToInventory("Cloak");
    super.setDamage(35);
    super.setPlayerClass(PlayerClass.ROGUE);
    super.setUnitManaMax(75);
    super.setCriticalChance(20);
    super.setArmour(10);
  }

  /**
   * Creates a Rogue with the default entity name of "Rogue".
   */
  public Rogue() {

  }
}
