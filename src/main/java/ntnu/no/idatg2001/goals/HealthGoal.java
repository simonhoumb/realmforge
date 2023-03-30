package ntnu.no.idatg2001.goals;

import ntnu.no.idatg2001.entityinformation.Entity;

public class HealthGoal implements Goal  {
  private int minimumHealth;

  public HealthGoal(int minimumHealth) {
    this.minimumHealth = minimumHealth;
  }

  public boolean isFulfilled(Entity entity) {
    return entity.getEntityHealth() >= minimumHealth;
  }
}
