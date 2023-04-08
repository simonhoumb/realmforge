package ntnu.no.idatg2001.BackEnd.goals;

import java.util.List;
import ntnu.no.idatg2001.BackEnd.entityinformation.Entity;

public class InventoryGoal implements Goal  {
  private List<String> mandatoryItems;

  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
  }

  public boolean isFulfilled(Entity entity) {
    return entity.getEntityInventory().equals(mandatoryItems);
  }
}
