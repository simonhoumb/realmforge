package no.ntnu.idatg2001.backend.actions;


import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class ItemAction extends Action {

  public ItemAction(String item) {
    this.value = item;
    this.setActionType(ActionType.ITEM);
  }

  public ItemAction() {

  }

  public void execute(Unit unit) {
    unit.addToInventory((String) value);
  }
}
