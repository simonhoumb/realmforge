package ntnu.no.idatg2001.goals;

import java.util.List;
import ntnu.no.idatg2001.playerinformation.Player;

public class InventoryGoal {
  private List<String> mandatoryItems;

  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
  }

  public boolean isFulfilled(Player player) {
    return player.getPlayerInventory().equals(mandatoryItems);
  }
}
