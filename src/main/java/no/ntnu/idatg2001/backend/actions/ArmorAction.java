package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class ArmorAction extends Action {

  public ArmorAction(double armor) {
    this.value = armor;
    this.setActionType(ActionType.ARMOR);
  }

  public ArmorAction() {

  }

  public void execute(Unit unit) {
    if (value instanceof Double) {
      int armor = Integer.parseInt(value.toString());
      unit.setArmour(unit.getArmour() + armor);
    }
  }
}