package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

/**
 * The WeaponAction class represents an action that changes the weapon of a unit.
 */
@Entity
public class
WeaponAction extends Action {

  /**
   * Creates an WeaponAction with the specified weapon.
   *
   * @param weapon the weapon
   */
  public WeaponAction(String weapon) {
    this.value = weapon;
    this.setActionType(ActionType.WEAPON);
  }

  /**
   * Creates an WeaponAction with the default weapon value of 1.
   */
  public WeaponAction() {

  }

  /**
   * Executes the action on the specified unit.
   *
   * @param unit the unit to execute the action on
   */
  public void execute(Unit unit) {
    String weapon = (String) value;
    unit.setWeapon(weapon);
  }
}
