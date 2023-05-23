package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The HealthAction class represents an action that increases the health of a unit.
 */
@Entity
public class HealthAction extends Action {

  /**
   * Creates an HealthAction with the specified health value.
   *
   * @param health the health value
   */
  public HealthAction(int health) {
    this.value = health;
    this.setActionType(ActionType.HEALTH);
  }

  /**
   * Creates an HealthAction with the default health value of 1.
   */
  public HealthAction() {

  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    int health = Integer.parseInt(value.toString());
    int currentHealth = unit.getUnitHealth();
    int newHealth = currentHealth + health;
    unit.setUnitHealth(newHealth);
  }
}
