package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class Ranger extends Unit {

  public Ranger(String entityName) {
    super(100, 100, entityName, 0, 50);
    this.setUnitHealth(150);
    super.addToInventory("Bow");
    super.addToInventory("HealthPotion");
    super.addToInventory("Trap");
    super.addToInventory("Boots");
    this.setPlayerClass(PlayerClass.RANGER);
    super.setIntelligence(4);
    super.setDexterity(10);
    super.setStrength(3);
    super.setLuck(5);
  }

  public Ranger() {

  }
}
