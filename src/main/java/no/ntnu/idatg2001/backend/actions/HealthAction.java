package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class HealthAction extends Action {

  public HealthAction(int health) {
    this.value = health;
    this.setActionType(ActionType.HEALTH);
  }

  public HealthAction() {

  }
  public void execute(Unit unit) {
    unit.setUnitHealth(unit.getUnitHealth() + Integer.parseInt(value.toString()));
  }
}
