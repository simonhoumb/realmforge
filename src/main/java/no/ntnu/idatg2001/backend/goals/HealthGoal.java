package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The HealthGoal class represents a health goal in a game.
 */
@Entity
public class HealthGoal extends Goal  {

  /**
   * Constructor for HealthGoal.
   *
   * @param minimumHealth The minimum amount of health that the unit has to have.
   */
  public HealthGoal(int minimumHealth) {
    this.goalValue = minimumHealth;
    setGoalType(GoalType.HEALTH_GOAL);
  }

  /**
   * Constructor for HealthGoal.
   */
  public HealthGoal() {

  }

  /**
   * isFulfilled checks if the goal is fulfilled.
   *
   * @param unit The unit that is playing the game.
   * @return true if the goal is fulfilled, false if not.
   */
  public boolean isFulfilled(Unit unit) {
    return unit.getUnitHealth() >= Integer.parseInt(goalValue.toString());
  }

  /**
   * setGoalValue sets the goal value.
   *
   * @param value The value to set.
   */
  @Override
  public void setGoalValue(Object value) throws UnsupportedOperationException {
    if (value instanceof Integer) {
      this.goalValue = value;
    } else {
      throw new UnsupportedOperationException("HealthGoal only accepts integers");
    }
  }
}

