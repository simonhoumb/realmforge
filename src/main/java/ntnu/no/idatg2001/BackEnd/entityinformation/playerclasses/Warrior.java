package ntnu.no.idatg2001.BackEnd.entityinformation.playerclasses;

import ntnu.no.idatg2001.BackEnd.entityinformation.PlayerClass;
import ntnu.no.idatg2001.BackEnd.entityinformation.Entity;

public class Warrior extends Entity {

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
}
