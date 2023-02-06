package ntnu.no.idatg2001.playerinformation;

public class Warrior extends Player {

  public Warrior(String playerName) {

    super(playerName);
    this.setPlayerHealthPoints(200);
    this.setPlayerClass(PlayerClass.WARRIOR);
    super.addToInventory("Sword");
    super.addToInventory("Health Potion");
    super.addToInventory("Shield");
  }}
