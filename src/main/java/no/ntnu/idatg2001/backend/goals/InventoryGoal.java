package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The InventoryGoal class represents a goal that is fulfilled when the player has a certain item in
 * their inventory.
 */
@Entity
public class InventoryGoal extends Goal  {

  /**
   * Constructor for InventoryGoal.
   *
   * @param mandatoryItem The item that the player has to have in their inventory.
   */
  public InventoryGoal(String mandatoryItem) {
    this.goalValue = mandatoryItem;
    setGoalType(GoalType.INVENTORY_GOAL);
  }

  /**
   * Constructor for InventoryGoal.
   */
  public InventoryGoal() {

  }

  /**
   * isFulfilled checks if the goal is fulfilled.
   *
   * @param unit The unit that is playing the game.
   * @return true if the goal is fulfilled, false if not.
   */
  public boolean isFulfilled(Unit unit) {
    return unit.getUnitInventory().contains(goalValue.toString());
  }

  /**
   * setGoalValue sets the goal value.
   *
   * @param value The value to set.
   */
  @Override
  public void setGoalValue(Object value) throws UnsupportedOperationException {
    if (value instanceof String) {
      this.goalValue = value;
    } else {
      throw new UnsupportedOperationException("InventoryGoal only accepts strings");
    }
  }
}

