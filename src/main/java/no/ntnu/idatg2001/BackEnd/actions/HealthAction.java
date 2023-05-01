package no.ntnu.idatg2001.BackEnd.actions;

import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;

public class HealthAction implements Action {
  int health;

  public HealthAction(int health) {
    this.health = health;
  }

  public void execute(Entity entity) {
    entity.setEntityHealth(entity.getEntityHealth() + health);
  }
}
