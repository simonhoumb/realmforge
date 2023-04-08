package ntnu.no.idatg2001.BackEnd.entityinformation.playerclasses;

import ntnu.no.idatg2001.BackEnd.entityinformation.Entity;
import ntnu.no.idatg2001.BackEnd.entityinformation.PlayerClass;

public class Rogue extends Entity {

  public Rogue(String playerName) {
    super(75, 75, playerName, 0, 75);
    super.addToInventory("Dagger");
    super.addToInventory("HealthPotion");
    super.addToInventory("PoisonBottle");
    super.addToInventory("Cloak");
    this.setPlayerClass(PlayerClass.ROGUE);
    super.setIntelligence(5);
    super.setDexterity(5);
    super.setStrength(2);
    super.setLuck(10);
  }

}
