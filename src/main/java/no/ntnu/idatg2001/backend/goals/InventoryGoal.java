package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class InventoryGoal extends Goal  {
  private List<String> mandatoryItems;

  public InventoryGoal(List<String> mandatoryItems) {
    this.mandatoryItems = mandatoryItems;
  }

  public InventoryGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitInventory().equals(mandatoryItems);
  }
}
