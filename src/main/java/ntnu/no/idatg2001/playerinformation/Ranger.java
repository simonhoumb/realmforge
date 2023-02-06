package ntnu.no.idatg2001.playerinformation;

public class Ranger extends Player {

  public Ranger(String playerName) {
    super(playerName);
    this.setPlayerHealthPoints(150);
    super.addToInventory("Bow");
    super.addToInventory("HealthPotion");
    super.addToInventory("Trap");
    super.addToInventory("Boots");
    this.setPlayerClass(PlayerClass.RANGER);
  }
}
