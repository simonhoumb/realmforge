package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class Ranger extends Unit {

  public Ranger(String entityName) {
    super(150, 150, entityName, 0, 50);
    super.addToInventory("Bow");
    super.addToInventory("HealthPotion");
    super.addToInventory("Trap");
    super.addToInventory("Boots");
    super.setDamage(25);
    super.setCriticalChance(20);
    super.setArmour(15);
    super.setUnitManaMax(50);
  }

  public Ranger() {

  }
}
