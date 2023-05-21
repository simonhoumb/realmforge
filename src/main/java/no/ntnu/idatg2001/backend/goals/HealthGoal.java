package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class HealthGoal extends Goal  {


  public HealthGoal(int minimumHealth) {
    this.goalValue = minimumHealth;
    setGoalType(GoalType.HEALTH_GOAL);
  }

  public HealthGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitHealth() >= (Integer) goalValue;
  }

  @Override
  public void setGoalValue(int value) {

  }
}
