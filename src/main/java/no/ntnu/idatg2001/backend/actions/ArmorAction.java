package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The ArmorAction class represents an action that increases the armor of a unit.
 */
@Entity
public class ArmorAction extends Action {

  /**
   * Creates an ArmorAction with the specified armor value.
   *
   * @param armor the armor value
   */
  public ArmorAction(Integer armor) {
    this.value = armor;
    this.setActionType(ActionType.ARMOR);
  }

  /**
   * Creates an ArmorAction with the default armor value of 1.
   */
  public ArmorAction() {}

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    int armor = (Integer) value;
    unit.setArmour(unit.getArmour() + armor);
  }
}