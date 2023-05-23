package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The ScoreGoal class represents a goal that is fulfilled when the player has a certain score.
 */
@Entity
public class ScoreGoal extends Goal {

  /**
   * Constructor for ScoreGoal.
   *
   * @param minimumPoints The minimum amount of points that the unit has to have.
   */
  public ScoreGoal(int minimumPoints) {
    this.goalValue = minimumPoints;
    setGoalType(GoalType.SCORE_GOAL);
  }

  /**
   * Constructor for ScoreGoal.
   */
  public ScoreGoal() {

  }

  /**
   * isFulfilled checks if the goal is fulfilled.
   *
   * @param unit The unit that is playing the game.
   * @return true if the goal is fulfilled, false if not.
   */
  public boolean isFulfilled(Unit unit) {
    return unit.getUnitScore() >= Integer.parseInt(goalValue.toString());
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
      throw new UnsupportedOperationException("ScoreGoal only accepts integers");
    }
  }
}

