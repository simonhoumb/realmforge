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
    super.setIntelligence(2);
    super.setDexterity(5);
    super.setStrength(10);
    super.setLuck(3);
  }

  public Warrior() {

  }
}
