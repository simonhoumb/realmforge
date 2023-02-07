package ntnu.no.idatg2001.goals;

import java.util.List;
import ntnu.no.idatg2001.entityinformation.Entity;

public class InventoryGoal implements Goal  {
  private List<String> mandatoryItems;

  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
  }

  public boolean isFulfilled(Entity entity) {
    return entity.getPlayerInventory().equals(mandatoryItems);
  }
}
