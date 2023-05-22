package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class Mage extends Unit {

  public Mage(String entityName) {
    super(100, 100, entityName, 0, 50);
    this.setUnitHealth(150);
    super.addToInventory("Staff");
    super.addToInventory("Health Potion");
    super.addToInventory("Mana Potion");
    super.addToInventory("Mage Robe");
    this.setPlayerClass(PlayerClass.MAGE);
    super.setDamage(25);
    super.setCriticalChance(20);
    super.setArmour(15);
    super.setUnitManaMax(50);
  }

  public Mage() {}
}
