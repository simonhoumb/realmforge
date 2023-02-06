package ntnu.no.idatg2001.playerinformation;

public class Mage extends Player {
  private int mana;

  public Mage(String playerName) {

    super(playerName);
    this.setPlayerHealthPoints(100);
    this.mana = 100;
    this.setPlayerClass(PlayerClass.MAGE);
    super.addToInventory("Staff");
    super.addToInventory("Mana Potion");
    super.addToInventory("Mage Robe");
  }



}
