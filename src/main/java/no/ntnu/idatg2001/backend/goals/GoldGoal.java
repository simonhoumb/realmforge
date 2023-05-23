package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The GoldGoal class represents a gold goal in a game.
 */
@Entity
public class GoldGoal extends Goal  {

  /**
   * Constructor for GoldGoal.
   *
   * @param goldMinimum The minimum amount of gold that the unit has to have.
   */
  public GoldGoal(int goldMinimum) {
    this.goalValue = goldMinimum;
    setGoalType(GoalType.GOLD_GOAL);
  }

  /**
   * Constructor for GoldGoal.
   */
  public GoldGoal() {

  }

  /**
   * isFulfilled checks if the goal is fulfilled.
   *
   * @param unit The unit that is playing the game.
   * @return true if the goal is fulfilled, false if not.
   */
  public boolean isFulfilled(Unit unit) {
    return unit.getGold() >= Integer.parseInt(goalValue.toString());
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
      throw new UnsupportedOperationException("GoldGoal only accepts integers");
    }
  }
}
