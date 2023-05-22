package no.ntnu.idatg2001.backend.actions;

import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class ArmorAction extends Action {

  public ArmorAction(Integer armor) {
    this.value = armor;
    this.setActionType(ActionType.ARMOR);
  }

  public ArmorAction() {}

  public void execute(Unit unit) {
    int armor = (Integer) value;
    unit.setArmour(unit.getArmour() + armor);
  }
}