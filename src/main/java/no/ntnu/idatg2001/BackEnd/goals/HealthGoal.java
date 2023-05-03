package no.ntnu.idatg2001.BackEnd.goals;

import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

public class HealthGoal extends Goal  {
  private int minimumHealth;

  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitHealth() >= minimumHealth;
  }
}
