package no.ntnu.idatg2001.BackEnd.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

@Entity
public class HealthGoal extends Goal  {
  private int minimumHealth;

  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  public HealthGoal() {

  }

  public boolean isFulfilled(Unit unit) {
    return unit.getUnitHealth() >= minimumHealth;
  }
}
