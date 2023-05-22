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
    int health = (Integer) value;
    int currentHealth = unit.getUnitHealth();
    int newHealth = currentHealth + health;
    unit.setUnitHealth(newHealth);
  }
}
