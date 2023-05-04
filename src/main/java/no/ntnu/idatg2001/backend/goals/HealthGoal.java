package no.ntnu.idatg2001.backend.goals;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

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
