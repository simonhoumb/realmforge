package no.ntnu.idatg2001.backend.actions;


import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class InventoryAction extends Action {

  public InventoryAction(String item) {
    this.value = item;
    this.setActionType(ActionType.ITEM);
  }

  public InventoryAction() {

  }

  public void execute(Unit unit) {
    unit.addToInventory((String) value);
  }
}
