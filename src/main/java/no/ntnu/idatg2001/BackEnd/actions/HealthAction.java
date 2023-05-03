package no.ntnu.idatg2001.BackEnd.actions;

import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

public class HealthAction extends Action {
  int health;

  public HealthAction(int health) {
    this.health = health;
  }

  public void execute(Unit unit) {
    unit.setEntityHealth(unit.getEntityHealth() + health);
  }
}
