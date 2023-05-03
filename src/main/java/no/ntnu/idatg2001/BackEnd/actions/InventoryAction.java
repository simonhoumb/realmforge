package no.ntnu.idatg2001.BackEnd.actions;


import no.ntnu.idatg2001.BackEnd.entityinformation.Unit;

public class InventoryAction extends Action {
  String item;

  public InventoryAction(String item) {
    this.item = item;
  }

  public void execute(Unit unit) {
    unit.addToInventory(item);
  }
}
