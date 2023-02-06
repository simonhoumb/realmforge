package ntnu.no.idatg2001.playerinformation;

import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {
  private int mana;

  public Mage(String playerName, int playerHealthPoints, int playerScore, int playerGold,
      PlayerClass playerClass, int mana) {

    super(playerName, playerHealthPoints, playerScore, playerGold, playerClass);
    this.mana = mana;
    super.addToInventory("Staff");
    super.addToInventory("Mana Potion");
    super.addToInventory("Mage Robe");
  }



}
