package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The Ranger class represents a Ranger.
 */
@Entity
public class Ranger extends Unit {

  /**
   * Creates a Ranger with the specified entity name.
   *
   * @param entityName the entity name
   */
  public Ranger(String entityName) {
    super(150, 150, entityName, 0, 50);
    super.addToInventory("Bow");
    super.addToInventory("HealthPotion");
    super.addToInventory("Trap");
    super.addToInventory("Boots");
    super.setDamage(25);
    super.setPlayerClass(PlayerClass.RANGER);
    super.setCriticalChance(20);
    super.setArmour(15);
    super.setUnitManaMax(50);
  }

  /**
   * Creates a Ranger with the default entity name of "Ranger".
   */
  public Ranger() {

  }
}
