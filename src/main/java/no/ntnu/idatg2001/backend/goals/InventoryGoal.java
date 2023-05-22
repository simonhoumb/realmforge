package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import java.util.List;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class InventoryGoal extends Goal  {

  public InventoryGoal(String mandatoryItem) {
    this.goalValue = mandatoryItem;
    setGoalType(GoalType.INVENTORY_GOAL);
  }

  public InventoryGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitInventory().contains(goalValue.toString());
  }

  @Override
  public void setGoalValue(Object value) {

  }
}
