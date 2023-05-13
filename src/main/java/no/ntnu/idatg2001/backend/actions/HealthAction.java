package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class HealthAction extends Action {
  int health;

  public HealthAction(int health) {
    this.health = health;
  }

  public HealthAction() {

  }
  public void execute(Unit unit) {
    unit.setUnitHealth(unit.getUnitHealth() + health);
  }
}
