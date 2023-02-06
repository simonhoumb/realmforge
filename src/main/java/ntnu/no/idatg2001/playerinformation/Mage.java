package ntnu.no.idatg2001.playerinformation;

import java.util.ArrayList;
import java.util.List;

public class Mage extends Player {
  private int mana;

  public Mage(String playerName) {

    super(playerName);
    this.mana = (100);
    super.addToInventory("Staff");
    super.addToInventory("Mana Potion");
    super.addToInventory("Mage Robe");
  }



}
