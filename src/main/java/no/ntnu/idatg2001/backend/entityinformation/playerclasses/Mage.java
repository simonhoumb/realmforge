package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class Mage extends Unit {
  public Mage(String entityName) {

    super(100, 100, entityName, 0, 100);
    super.addToInventory("Staff");
    super.addToInventory("Mana Potion");
    super.addToInventory("Mage Robe");
    super.setUnitManaMax(100);
    super.setDamage(30);
    super.setCriticalChance(30);
    super.setArmour(10);
  }

  public Mage() {

  }
}
