package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;

@Entity
public class Rogue extends Unit {

  public Rogue(String playerName) {
    super(75, 75, playerName, 0, 75);
    super.addToInventory("Dagger");
    super.addToInventory("HealthPotion");
    super.addToInventory("PoisonBottle");
    super.addToInventory("Cloak");
    this.setPlayerClass(PlayerClass.ROGUE);
    super.setDamage(35);
    super.setUnitManaMax(75);
    super.setCriticalChance(20);
    super.setArmour(10);
  }

  public Rogue() {

  }
}
