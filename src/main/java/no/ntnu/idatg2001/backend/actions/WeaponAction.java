package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class WeaponAction extends Action {

  public WeaponAction(String weapon) {
    this.value = weapon;
    this.setActionType(ActionType.WEAPON);
  }

  public WeaponAction() {

  }

  public void execute(Unit unit) {
    if (value instanceof String) {
      String weapon = (String) value;
      unit.setWeapon(weapon);
    }
  }
}
