package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The Warrior class represents a Warrior.
 */
@Entity
public class Warrior extends Unit {

  /**
   * Creates a Warrior with the specified entity name.
   *
   * @param playerName the entity name
   */
  public Warrior(String playerName) {
    super(200, 200, playerName, 0, 50);
    super.addToInventory("GreatSword");
    super.addToInventory("HealthPotion");
    super.addToInventory("Shield");
    super.addToInventory("BodyArmor");
    super.setDamage(20);
    super.setPlayerClass(PlayerClass.WARRIOR);
    super.setCriticalChance(5);
    super.setArmour(25);
    super.setUnitManaMax(50);
  }

  /**
   * Creates a Warrior with the default entity name of "Warrior".
   */
  public Warrior() {

  }
}
