package ntnu.no.idatg2001.actions;

import ntnu.no.idatg2001.entityinformation.Entity;

public class HealthAction implements Action {
  int health;

  public HealthAction(int health) {
    this.health = health;
  }

  public void execute(Entity entity) {
    entity.setEntityHealth(entity.getEntityHealth() + health);
  }
}
