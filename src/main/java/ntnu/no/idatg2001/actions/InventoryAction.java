package ntnu.no.idatg2001.actions;

import ntnu.no.idatg2001.Player;

public class InventoryAction implements Action {
  String item;

  public InventoryAction(String item) {
    this.item = item;
  }

  public void execute(Player player) {
    player.addToInventory(item);
  }
}
