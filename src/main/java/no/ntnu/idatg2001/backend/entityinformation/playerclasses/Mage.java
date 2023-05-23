package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The Mage class represents a mage.
 */
@Entity
public class Mage extends Unit {

  /**
   * Creates a Mage with the specified entity name.
   *
   * @param entityName the entity name
   */
  public Mage(String entityName) {
    super(100, 100, entityName, 0, 100);
    this.setUnitHealth(150);
    super.addToInventory("Staff");
    super.addToInventory("Health Potion");
    super.addToInventory("Mana Potion");
    super.addToInventory("Mage Robe");
    super.setDamage(30);
    super.setCriticalChance(30);
    super.setArmour(10);
    super.setPlayerClass(PlayerClass.MAGE);
    super.setUnitManaMax(100);
  }

  /**
   * Creates a Mage with the default entity name of "Mage".
   */
  public Mage() {}
}
