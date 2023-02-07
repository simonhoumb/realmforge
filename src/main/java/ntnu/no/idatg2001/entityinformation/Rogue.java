package ntnu.no.idatg2001.playerinformation;

public class Rogue extends Player {

  public Rogue(String playerName) {
    super(playerName);
    this.setPlayerHealthPoints(100);
    this.setPlayerClass(PlayerClass.ROGUE);
    super.addToInventory("Dagger");
    super.addToInventory("Poison Bomb");
    super.addToInventory("Cloak");
  }
}
