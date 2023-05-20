package no.ntnu.idatg2001.backend.entityinformation.playerclasses;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.PlayerClass;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class Warrior extends Unit {

  public Warrior(String playerName) {
    super(200, 200, playerName, 0, 50);
    super.addToInventory("GreatSword");
    super.addToInventory("HealthPotion");
    super.addToInventory("Shield");
    super.addToInventory("BodyArmor");
    this.setPlayerClass(PlayerClass.WARRIOR);
    super.setDamage(20);
    super.setCriticalChance(5);
    super.setArmour(25);
    super.setUnitManaMax(50);
  }

  public Warrior() {

  }
}
