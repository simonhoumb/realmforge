package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class GoldGoal extends Goal  {

  public GoldGoal(int goldMinimum) {
    this.goalValue = goldMinimum;
    setGoalType(GoalType.GOLD_GOAL);
  }

  public GoldGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getGold() >= (Integer) goalValue;
  }

  @Override
  public void setGoalValue(int value) {

  }
}
