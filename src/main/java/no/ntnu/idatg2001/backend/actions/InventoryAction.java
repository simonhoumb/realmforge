package no.ntnu.idatg2001.backend.actions;


import jakarta.persistence.Entity;
import no.ntnu.idatg2001.backend.entityinformation.Unit;

@Entity
public class InventoryAction extends Action {
  String item;

  public InventoryAction(String item) {
    this.item = item;
  }

  public InventoryAction() {

  }

  public void execute(Unit unit) {
    unit.addToInventory(item);
  }
}
