package ntnu.no.idatg2001.entityinformation.playerclasses;

import ntnu.no.idatg2001.entityinformation.Entity;

public class Mage extends Entity {
  public Mage(String entityName) {

    super(100, 100, entityName, 0, 100);
    super.addToInventory("Staff");
    super.addToInventory("Mana Potion");
    super.addToInventory("Mage Robe");
    super.setIntelligence(10);
    super.setDexterity(5);
    super.setStrength(2);
    super.setLuck(4);
  }



}
