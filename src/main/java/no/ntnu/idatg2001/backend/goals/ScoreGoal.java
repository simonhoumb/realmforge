package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class ScoreGoal extends Goal {

  public ScoreGoal(int minimumPoints) {
    this.goalValue = minimumPoints;
    setGoalType(GoalType.SCORE_GOAL);
  }

  public ScoreGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitLevel() >= (Integer) goalValue;
  }

  @Override
  public void setGoalValue(int value) {

  }
}
