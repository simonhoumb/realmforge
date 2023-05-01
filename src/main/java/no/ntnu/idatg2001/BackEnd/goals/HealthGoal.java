package no.ntnu.idatg2001.BackEnd.goals;

import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;

public class HealthGoal implements Goal  {
  private int minimumHealth;

  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  public boolean isFulfilled(Entity entity) {
    return entity.getEntityHealth() >= minimumHealth;
  }
}
