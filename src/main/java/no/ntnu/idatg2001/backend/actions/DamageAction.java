package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The DamageAction class represents an action that damages a unit.
 */
@Entity
public class DamageAction extends Action {

  /**
   * Creates a DamageAction with the specified damage value.
   *
   * @param damage the damage value
   */
  public DamageAction(int damage) {
    this.value = damage;
    this.setActionType(ActionType.DAMAGE);
  }

  /**
   * Creates a DamageAction with the default damage value of 1.
   */
  public DamageAction() {}

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    int damage = Integer.parseInt(value.toString());
    int modifiedDamage = calculateModifiedDamage(damage);
    unit.setUnitHealth(unit.getUnitHealth() - modifiedDamage);
  }

  /**
   * Calculates the modified damage based on the unit attributes and game rules.
   *
   * @param damage the damage value
   * @return the modified damage value
   */
  private int calculateModifiedDamage(int damage) {
    // You can add any additional logic to modify the damage based on unit attributes or game rules
    // For example, you can consider armor, luck, or any other factors affecting damage calculation
    return damage;
  }
}