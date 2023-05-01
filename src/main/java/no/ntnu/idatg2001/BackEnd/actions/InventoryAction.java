package no.ntnu.idatg2001.BackEnd.actions;


import no.ntnu.idatg2001.BackEnd.entityinformation.Entity;

public class InventoryAction implements Action {
  String item;

  public InventoryAction(String item) {
    this.item = item;
  }

  public void execute(Entity entity) {
    entity.addToInventory(item);
  }
}
